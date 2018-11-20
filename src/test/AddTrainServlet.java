package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AddTrainServlet extends GenericServlet
{
	public Connection con;
	
	@Override
	public void init()
	{
		con=DBConnection.getCon();
	}
	
	@Override
	public  void service(ServletRequest req,ServletResponse res) throws IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text.html");
		
		String trainID=req.getParameter("trainID");
		String trainName=req.getParameter("trainName");
		String frmStn=req.getParameter("fromStn");
		String toStn=req.getParameter("toStn");
		int avlBrth=Integer.parseInt(req.getParameter("avlBrth"));
		
		try
		{
			PreparedStatement ps=con.prepareStatement("insert into TrainInfo values(?,?,?,?,?)");
			ps.setString(1, trainID);
			ps.setString(2, trainName);
			ps.setString(3, frmStn);
			ps.setString(4, toStn);
			ps.setInt(5, avlBrth);
			
			int r=ps.executeUpdate();
			if(r>0)
			{
				pw.println("1 Row Inserted");
				pw.println("<a href='RetrieveTrain.html'>View Train info</a>");
				pw.println("<a href='AddTrain.html'>Add Train</a>");
				pw.println("<a href='login.html'>Logout</a>");
				
			}
			else
			{
				pw.println("Table NOT Updated Successfully !!");
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
