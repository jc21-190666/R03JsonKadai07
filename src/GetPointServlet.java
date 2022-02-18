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
@WebServlet("/getPoint")

public class GetPointServlet extends HttpServlet {
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
				final String driverClassName = "com.mysql.jdbc.Driver";
				final String url ="jdbc:mysql://192.168.54.190:3306/jsonkadai07";
				final String user ="jsonkadai07";
				final String password ="JsonKadai07";
				
				
				
				
				
				try {
					Class.forName(driverClassName);
					
					//DBと接続
					Connection con = DriverManager.getConnection(url,user,password);
					int point = 0;
					
					PreparedStatement st =
							con.prepareStatement( //pointをselect
									"select point from point_mst where tenpo_id = ? and user_id = ?"
								); 
					
					PreparedStatement st2 =
							//データないとき
							con.prepareStatement( //データがないときpointに５００を設定して
									"insert into point_mst values(?,?,500)"
								); 
					
					String tenpo_id =request.getParameter("TENPO_ID");
					String user_id =request.getParameter("USER_ID");

					
					//stのsqlに１，２に設定
					st.setString(1, tenpo_id);
					st.setString(2, user_id);
					
					//stのsqlを実行
					ResultSet rs = st.executeQuery();
					
					
					//ポイントのデータがあったら
					if(rs.next() == true) {
						 point = rs.getInt("point");
						 
						 //データがないとき
					}else {
						
						//st2に１，２を設定する
						st2.setString(1,tenpo_id);
						st2.setString(2,user_id);
						point  = 500;
						
						//st2のsqlを実行
						st2.executeUpdate();
					}
					
					
					rs.close();
					st.close();
					con.close();
					
					request.setAttribute("point", point);
					
					//フォワード
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
					rd.forward(request, response);
					
				}catch (SQLException e ) {
					e.printStackTrace();
				
				} catch (ClassNotFoundException e ) {
					
					e.printStackTrace();
					
				}

				//pointをsetする
				
	}
}
