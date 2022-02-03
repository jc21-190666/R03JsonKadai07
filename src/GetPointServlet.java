import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getPoint")

public class GetPointServlet extends HttpServlet {​
	private static final long serialVersionUID = 1L;
	/**
* @see HttpServlet#HttpServlet()
*/
	public GetPointServlet() {​​
		super();
	}​

	/**
* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
* response)
*/
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {​​
		try {
			Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/＜jsonkadai07＞?useSSL=false",
				"jsonkadai07",
				"<JsonKadai07>"
				);
	// SQL文の実行
		PreparedStatement pstmt = con.prepareStatement("select * from users");
		ResultSet rs = pstmt.executeQuery();
		// 検索結果を表示
		while (rs.next()) {​
			System.out.println(rs.getString("name"));
			System.out.println(rs.getInt("address"));
		}​​
		// 後処理（リソースのクローズ）
		rs.close();
		pstmt.close();
		con.close();
		}catch (SQLException e) {​​
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		}
	}
}