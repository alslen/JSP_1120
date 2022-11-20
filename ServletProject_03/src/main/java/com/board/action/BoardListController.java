package com.board.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.BoardDTO;
import com.board.model.SBoardDAO;
import com.board.model.SBoardDAOImpl;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/board/boardlist")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		SBoardDAO dao = SBoardDAOImpl.getInstance();
		ArrayList<BoardDTO> arr = new ArrayList<BoardDTO>();
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) {  // 맨 첫시작에 페이지 번호가 없기 때문에
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);  // 현재 페이지
		int pageSize = 5; // 한 화면에 보여지는 게시물 수
		int pageBlock = 3;  // 한 페이지에 보여줄 페이지 블럭
		
		// 한 화면에 보여지는 게시물 번호
		int startRow = (currentPage-1)*pageSize+1;  // 1 6 11
		int endRow = currentPage * pageSize;  // 5 10 15
		
		String field="";
		String word="";
		if(request.getParameter("word")!= null) {  // 검색이면
			field = request.getParameter("field");
			word = request.getParameter("word");
			arr = dao.boardList(startRow, endRow, field, word);
		} else {
			arr = dao.boardList();
		}
		
		int count = dao.boardCount(field, word);  // 검색을 포함한 개수 구하기
		
		request.setAttribute("count", count);
		request.setAttribute("arr", arr);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("boardList.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
