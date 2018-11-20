package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RegServlet extends GenericServlet
{
	public Connection con;
	
	@Override
	public void init() throws ServletException
	{
		con=DBConnection.getCon();
	}
	@Override
	public void service(ServletRequest req,ServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		
		String uName=req.getParameter("rUname");
		String pWord=req.getParameter("rPword");
		String fName=req.getParameter("rFname");
		String lName=req.getParameter("rLname");
		String addr=req.getParameter("rAddr");
		long phNo=Long.parseLong(req.getParameter("rPhNo"));
		String eMail=req.getParameter("rEmail");
		
		try
		{
			PreparedStatement ps=con.prepareStatement("insert into BookReg14 values(?,?,?,?,?,?,?)");
			ps.setString(1, uName);
			ps.setString(2, pWord);
			ps.setString(3, fName);
			ps.setString(4, lName);
			ps.setString(5, addr);
			ps.setLong(6, phNo);
			ps.setString(7, eMail);
			
			int r=ps.executeUpdate();
			if(r>0)
			{
				pw.println("User Registered Successfully!!");
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
