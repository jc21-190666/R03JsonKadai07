import java.io.Console;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
import com.sun.jndi.ldap.Connection;
import com.sun.net.httpserver.Authenticator.Result;
import com.sun.tools.javac.util.List;
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;


/**
 * Servlet implementation class GetPointServlet
 */

@WebServlet("/getPoint")

public class GetPointServlet extends HttpServlet {​
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetPointServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setCharacterEncoding("utf-8");
				String driverClassName = "com.mysql.jdbc.Driver";
				String url ="jdbc:mysql://192.168.54.190:3306/jsonkadai07";
				String user ="jsonkadai07";
				String password ="JsonKadai07";
				
				
				int point = 0;
				
				
				try {
					Class.forName(driverClassName);
					Connection con = DriverManager.getConnection(url,user,password);
					PreparedStatement st =
							con.prepareStatement(
									"select point from point_mst where  shop_id = ? and user_id = ?"
								); 
					PreparedStatement st2 =
							con.prepareStatement(
									"insert into point_mst values(?,?,500)"
								); 
					
					String shop_id =request.getParameter("shop_id");
					String user_id =request.getParameter("user_id");
					
										
					st.setString(1, shop_id);
					st.setString(2, user_id);			
					ResultSet rs = st.executeQuery();
					
					if(rs.next() == true) {
						 point = rs.getInt("point");
					}else {
						st2.setString(1,"shop_id");
						st2.setString(2,"user_id");
						
						st2.executeUpdate();
					}
					/*
					 * // SQL���̎�s PreparedStatement pstmt =
					 * con.prepareStatement("select * from point_mst"); ResultSet rs =
					 * pstmt.executeQuery();
					 */
					// ����ʂ�\��
					
					
					
					// �㏈���i���\�[�X�̃N���[�Y�j
					rs.close();
					st.close();
					con.close();
					
				}catch (SQLException e ) {
					// TODO �����������ꂽ catch �u���b�N
					e.printStackTrace();
				
				} catch (ClassNotFoundException e ) {
					
					e.printStackTrace();
					
				}

				request.setAttribute("point", point);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
				rd.forward(request, response);
	}
}

/*
 * final String driverName = "TEAM07"; final String url =
 * "jdbc:oracle:thin:@192.168.54.190:3306/pdborcl"; final String id =
 * "jsonkadai07"; final String pass = "JsonKadai07";
 * 
 * try { Class.forName(driverName); java.sql.Connection
 * connection=DriverManager.getConnection(url,id,pass); PreparedStatement st =
 * connection.prepareStatement("select * from point_mst"); ResultSet result =
 * st.executeQuery();
 * 
 * java.util.List<String[]> list = new ArrayList<>(); while(resulut.next()==
 * true) { String[] s = new String[1]; s[0] = result.getString("kari");
 * list.add(s); }
 * 
 * for(String[] s: list) { System.out.println(s[0]); }
 * request.setAttribute("list", list); request.getRequestDispatcher("pass");
 * 
 * } catch (ClassNotFoundException e ) { // TODO �����������ꂽ catch �u���b�N
 * e.printStackTrace(); } catch (SQLException e ) { // TODO �����������ꂽ catch �u���b�N
 * e.printStackTrace(); }
 * 
 * String point ="POINT"; String num = "3580";
 * 
 * request.setAttribute("point", point); request.setAttribute("num", num);
 * 
 * RequestDispatcher rd =
 * request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
 * rd.forward(request, response);
 */
