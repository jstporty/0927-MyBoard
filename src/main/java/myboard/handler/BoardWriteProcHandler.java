package myboard.handler;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import myboard.dao.BoardIDDAOImpl;
import myboard.dao.impl.BoardFileWriteDAOImpl;
import myboard.dao.impl.BoardWriteDAOImpl;
import myboard.dto.BoardDTO;
import myboard.dto.BoardFileDTO;
import myboard.service.BoardFileService;
import myboard.service.BoardIDService;
import myboard.service.BoardWriteService;

public class BoardWriteProcHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {

		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBtitle(request.getParameter("btitle") == null ? "" : request.getParameter("btitle"));
		boardDTO.setBdomain(request.getParameter("bdomain") == null ? "" : request.getParameter("bdomain"));
		boardDTO.setBcontent(request.getParameter("bcontent") == null ? "" : request.getParameter("bcontent"));
		boardDTO.setBwriterid("anonymous");

		BoardWriteService boardWriteService = new BoardWriteDAOImpl();
		try {
			boardWriteService.writeBoard(boardDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		BoardIDService boardIDService = new BoardIDDAOImpl();
		int bid = 0;
		try {
			bid = boardIDService.getBID();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		BoardFileService boardFileService = new BoardFileWriteDAOImpl();
		Collection<Part> parts = null;
		try {
			parts = request.getParts();
		} catch (Exception ex) {

		}
		List<String> sfnList = (List<String>) request.getAttribute("sfnList");
		List<String> contenttypeList = (List<String>) request.getAttribute("contenttypeList");
		int listIndex = 0;
		for (Part part : parts) {
			if (part.getHeader("Content-Disposition").contains("filename=") && part.getSize() > 0) {
				BoardFileDTO boardFileDTO = new BoardFileDTO();
				boardFileDTO.setBfcfn(part.getSubmittedFileName());
				boardFileDTO.setBfsfn(sfnList.get(listIndex));
				boardFileDTO.setBfsize((int) part.getSize());
				boardFileDTO.setBfcontenttype(contenttypeList.get(listIndex));
				try {
					boardFileService.writeBoardFile(bid, boardFileDTO);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				listIndex++;
			}
		}

		return "/boardlist.do";
	}

}
