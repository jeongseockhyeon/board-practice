package com.board_practice.board_practice.service;

import com.board_practice.board_practice.dto.BoardDTO;
import com.board_practice.board_practice.entity.BoardEntity;
import com.board_practice.board_practice.entity.BoardFileEntity;
import com.board_practice.board_practice.repository.BoardFileRepository;
import com.board_practice.board_practice.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        //파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile() == null || boardDTO.getBoardFile().isEmpty()) {
            //첨부 파일 없음
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(boardEntity);
        } else {
            // 첨부 파일 있음.
            /*
            * 1. DTO에 담긴 파일을 꺼냄
            * 2. 파일의 이름 가져옴
            * 3, 서버 저장용 이름을 만듦
            * 4. 저장 경로 설정
            * 5. 해당 경로에 파일 저장
            * 6. board_table에 해당 데이터 save 처리
            * 7. board_file_table에 해당 데이터 save 처리
            */
            MultipartFile boardFile = boardDTO.getBoardFile(); // 1
            String originalFileName = boardFile.getOriginalFilename(); // 2
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName; // 3
            String savePath = "C:/springboot_img/" + storedFileName; // 4
            boardFile.transferTo(new File(savePath)); // 5
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFileName, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }

    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }

        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
     boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id){
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public void update(Long id,BoardDTO boardDTO) {
        Optional<BoardEntity> savedBoardEntity = boardRepository.findById(id);
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(savedBoardEntity.get(), boardDTO);
        boardRepository.save(boardEntity);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable){
        int page = pageable.getPageNumber() - 1;
        //페이지 당 글 개수
        int pageLimit = 3;
        //한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        //page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(),board.getBoardWriter(),board.getBoardTitle(), board.getBoardHits(), board.getBoardCreatedTime()));

        return boardDTOS;

    }
}
