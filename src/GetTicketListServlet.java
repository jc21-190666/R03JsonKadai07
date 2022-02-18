
import java.awt.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getTicketList")
public class GetTicketListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetTicketListServlet() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		
		final String driverClassName = "com.mysql.jdbc.Driver";
		final String url ="jdbc:mysql://192.168.54.190:3306/jsonkadai07";
		final String user ="jsonkadai07";
		final String password ="JsonKadai07";
		
		try {
			Class.forName(driverClassName);
			Connection con = DriverManager.getConnection(url,user,password);
			
			String shop_id =request.getParameter("TENPO_ID");
			String user_id =request.getParameter("USER_ID");
			int point = 0;
			
			
			//ポイントを取り出す
			PreparedStatement st = con.prepareStatement("select point from point_mst where tenpo_id = ? and user_id = ?");
			
			//取り出したポイントで交換できるチケットを取り出す
			PreparedStatement stx = con.prepareStatement("select * from ticket_mst where point <= ? && tenpo_id = ?");
			
			
			//最初のsql文st
			st.setString(1, shop_id);
			st.setString(2, user_id);
			ResultSet rs = st.executeQuery();
			
			//1行目からにする
			rs.next();
			point = rs.getInt("point");
			
			//上のポイントをstxの?に入れる
			stx.setInt(1, point);
			stx.setString(2,shop_id);
			
			//stの結果をrsxに
			ResultSet rsx = stx.executeQuery();
			ArrayList<String[]> list= new ArrayList<>();
			
			
			
			
			while(rsx.next()){
			    /* 行からデータを取得 */
				String[] s = new String[3];
				
				//配列
				s[0] = rsx.getString("ticket_id");
				s[1] = rsx.getString("ticket_name");
				s[2] = rsx.getString("point");
				
				//listに追加
				list.add(s);
			  }
			request.setAttribute("list",list);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getTicketList.jsp");
			rd.forward(request,response);
			
			}catch (SQLException e){
			  System.out.println("SQLException:" + e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
		}		
	}	
}
