package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginServlet extends GenericServlet
{
	public Connection con;
	
	@Override
	public void init()
	{
		con=DBConnection.getCon();
	}
	@Override
	public void service(ServletRequest req, ServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		
		String uName=req.getParameter("uName");
		String pWord=req.getParameter("pWord");
		
		try
		{
			PreparedStatement ps=con.prepareStatement("Select * from BookReg14 where uname=? and pword=?");
			ps.setString(1, uName);
			ps.setString(2, pWord);
			
			int r=ps.executeUpdate();
			if(r>0)
			{
				pw.println("Welcome : "+uName);
				pw.println("<br>");
				pw.println("<a href='AddTrain.html'>Add Train </a>");
				pw.println("<a href='RetrieveTrain.html'>Get Train Details </a>");
				pw.println("<a href='login.html'>Logout </a>");
			}
			else
			{
				pw.println("NO record found!!");
				RequestDispatcher rd=req.getRequestDispatcher("login.html");
				rd.include(req, res);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
