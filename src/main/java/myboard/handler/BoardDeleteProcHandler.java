package myboard.handler;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myboard.dao.impl.BoardDeleteDAOImpl;
import myboard.dao.impl.BoardFileDeleteDAOImpl;
import myboard.dao.impl.BoardFileListDAOImpl;
import myboard.dto.BoardFileDTO;

public class BoardDeleteProcHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		Integer bid = Integer.parseInt(request.getParameter("bid") == null ? "" : request.getParameter("bid"));
		List<BoardFileDTO> boardFileDTOList = null;
		try {
			boardFileDTOList = new BoardFileListDAOImpl().listBoardFile(bid);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (BoardFileDTO boardFileDTO : boardFileDTOList) {
			File file = new File(boardFileDTO.getBfsfn());
			if (file.exists()) {
				file.delete();
			}
		}
		try {
			new BoardFileDeleteDAOImpl().deleteBoardFiles(bid);
			new BoardDeleteDAOImpl().deleteBoard(bid);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "/boardlist.do";
	}

}
