package myboard.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myboard.dao.impl.BoardFileUpdateDAOImpl;
import myboard.dao.impl.BoardUpdateDAOImpl;
import myboard.dto.BoardDTO;
import myboard.service.BoardFileService;
import myboard.service.BoardUpdateService;

public class BoardUpdateProcHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {

		int bid = Integer.parseInt(request.getParameter("bid"));
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBid(bid);
		boardDTO.setBtitle(request.getParameter("btitle") == null ? "" : request.getParameter("btitle"));
		boardDTO.setBdomain(request.getParameter("bdomain") == null ? "" : request.getParameter("bdomain"));
		boardDTO.setBcontent(request.getParameter("bcontent") == null ? "" : request.getParameter("bcontent"));
		boardDTO.setBwriterid("anonymous");

		BoardUpdateService boardUpdateService = new BoardUpdateDAOImpl();
		try {
			boardUpdateService.updateBoard(boardDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		BoardFileService boardFileService = new BoardFileUpdateDAOImpl();
		try {
			boardFileService.updateBoardFiles(bid, boardFileService.listBoardFile(bid));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "/boardlist.do";
	}

}
