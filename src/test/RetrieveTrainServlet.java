package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RetrieveTrainServlet extends GenericServlet
{
	public Connection con;
	
	@Override
	public void init()
	{
		con=DBConnection.getCon();
	}
	
	@Override
	public void service(ServletRequest req,ServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		
		String fromStn=req.getParameter("frmStn");
		String toStn=req.getParameter("toStn");
		try
		{
			PreparedStatement ps=con.prepareStatement("Select * from TrainInfo where fromstn=? and tostn=?");
			ps.setString(1, fromStn);
			ps.setString(2, toStn);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				pw.println("<br>Train ID: "+rs.getString(1)
				+"<br>Train Name: "+rs.getString(2)
				+"<br>From Station: "+rs.getString(3)
				+"<br>To Station: "+rs.getString(4)
				+"<br>Available berth :"+rs.getInt(5));
				
				pw.print("<br>");
				pw.println("<a href='RetrieveTrain.html'>View Train info</a>");
				pw.println("<a href='AddTrain.html'>Add Train</a>");
				pw.println("<a href='login.html'>Logout</a>");
			}
			else
			{
				pw.println("No Train Available between Station <br>");
				RequestDispatcher rd=req.getRequestDispatcher("login.html");
				rd.include(req, res);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
