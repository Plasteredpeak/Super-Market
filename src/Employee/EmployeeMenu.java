package Employee;

import Profiling.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeMenu extends JFrame implements ActionListener {
    Connection con;

    {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Project","Project");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    JButton ShowO,showP,C,Q,R;
    String username;
    public EmployeeMenu(String str){
        username=str;
        try {
            Statement q = con.createStatement();
            ResultSet rs = q.executeQuery("select * from employee where username = '" + username + "'");

            if(rs.next()) {

                setTitle(rs.getString(7));
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                setResizable(false);
                setSize(700, 600);
                setLayout(null);
                setLocationRelativeTo(null);
                ImageIcon mainlogo = new ImageIcon("images\\mainlogo.png");
                setIconImage(mainlogo.getImage());

                JPanel head = new JPanel(null);
                head.setBounds(0, 0, 700, 100);
                head.setBackground(new Color(44, 62, 80));
                JLabel heading = new JLabel(rs.getString(1) + " " + rs.getString(2));
                heading.setFont(new Font("Rooney", Font.BOLD, 40));
                heading.setBounds(15, 25, 300, 50);
                heading.setForeground(Color.white);
                JLabel email = new JLabel(rs.getString(5));
                email.setFont(new Font("Rooney", Font.PLAIN, 16));
                email.setBounds(500, 50, 300, 50);
                email.setForeground(Color.white);

                head.add(heading);
                head.add(email);


                JPanel body = new JPanel(null);
                body.setBounds(0, 100, 700, 500);
                body.setBackground(new Color(213, 245, 227));

                ShowO = new JButton("Approve Orders");
                ShowO.setFocusable(false);
                ShowO.setBounds(250, 50, 200, 65);
                ShowO.setFont(new Font("Bodoni", Font.BOLD, 18));
                ShowO.setBackground(new Color(40, 55, 71));
                ShowO.setForeground(Color.white);
                ShowO.setBorder(BorderFactory.createEtchedBorder());
                ShowO.addActionListener(this);

                showP = new JButton("Manage Stock");
                showP.setFocusable(false);
                showP.setBounds(250, 140, 200, 65);
                showP.setFont(new Font("Bodoni", Font.BOLD, 18));
                showP.setBackground(new Color(40, 55, 71));
                showP.setForeground(Color.white);
                showP.setBorder(BorderFactory.createEtchedBorder());
                showP.addActionListener(this);

                C = new JButton("Complain");
                C.setFocusable(false);
                C.setBounds(250, 230, 200, 65);
                C.setFont(new Font("Bodoni", Font.BOLD, 18));
                C.setBackground(new Color(40, 55, 71));
                C.setForeground(Color.white);
                C.setBorder(BorderFactory.createEtchedBorder());
                C.addActionListener(this);

                Q = new JButton("Queries");
                Q.setFocusable(false);
                Q.setBounds(250, 320, 200, 65);
                Q.setFont(new Font("Bodoni", Font.BOLD, 18));
                Q.setBackground(new Color(40, 55, 71));
                Q.setForeground(Color.white);
                Q.setBorder(BorderFactory.createEtchedBorder());
                Q.addActionListener(this);


                JButton back = new JButton("Log Out");
                back.setForeground(Color.white);
                back.setBackground(new Color(203, 67, 53));
                back.setBounds(570, 400, 90, 40);
                back.setFocusable(false);
                back.setFont(new Font("Tahoma", Font.BOLD, 14));
                back.addActionListener(E -> {
                    new MainMenu();
                    dispose();
                });

                R = new JButton("Resign");
                R.setForeground(Color.white);
                R.setBackground(new Color(175, 96, 26));
                R.setBounds(30, 400, 100, 40);
                R.setFocusable(false);
                R.setFont(new Font("Times New Roman", Font.BOLD, 18));
                R.addActionListener(this);

                body.add(back);
                body.add(ShowO);
                body.add(showP);
                body.add(C);
                body.add(Q);
                body.add(R);

                setVisible(true);
                add(head);
                add(body);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==R){
            int opt= JOptionPane.showConfirmDialog(null,"Are u sure you want to Quit Your job??","Confirmation",JOptionPane.YES_NO_OPTION);
            if(opt==0) {
                try {
                    Statement q = con.createStatement();
                    q.executeQuery("delete from employee where username = '"+username+"'");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"You Quit your Job!!","Good for u mate",JOptionPane.INFORMATION_MESSAGE);
                new MainMenu();
                dispose();
            }
        }
        if(e.getSource()==ShowO){
            new ApproveOrderMenu(username);
            dispose();
        }
        if(e.getSource()==showP){
            String[] C={"Beverages","Bakery Items","Snacks","Fruits & Vegetables"};
            String opt=(String)JOptionPane.showInputDialog(null,"Pick a Category","Choose",JOptionPane.QUESTION_MESSAGE,null,C,C[0]);
            if(opt != null) {
                new ManageStock(opt,username);
                dispose();
            }
        }
        if(e.getSource()==Q) {
            try{
                Statement s=con.createStatement();
                ResultSet rs=s.executeQuery("select * from customer_query");
            if (rs.isBeforeFirst()) {
                Object[] r = {"Close", "Delete", "Next"};

                Print:
                {
                    while(rs.next()) {
                        int id=rs.getInt(2);
                        int opt = JOptionPane.showOptionDialog(null, rs.getString(1), rs.getString(3),
                                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, r, 0);
                            if (opt == 0 || opt == -1) {
                                break Print;
                            }
                            else if (opt == 1) {
                                Statement st=con.createStatement();
                                st.executeQuery("delete from customer_query where query_id ="+id);
                            }
                        }
                    }
                }
            else
                JOptionPane.showMessageDialog(null, "No Queries Found", "gg", JOptionPane.INFORMATION_MESSAGE);
        }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }
        if(e.getSource()==C){
            String msg=JOptionPane.showInputDialog(null,"Enter Message","Complain",JOptionPane.INFORMATION_MESSAGE);
            if(msg!=null){
                try {
                    int id=0;
                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery("Select MAX(query_id) from employee_query");
                    if(rs.next()){
                        id=rs.getInt(1)+1;
                    }
                    PreparedStatement query = con.prepareStatement("insert into employee_query(msg,query_id,employee_username)"
                            + "values(?,?,?)");
                    query.setString(1,msg);
                    query.setInt(2,id);
                    query.setString(3,username);

                    query.execute();

                    JOptionPane.showMessageDialog(null,"Complain sent to Admin :)","Karen",JOptionPane.INFORMATION_MESSAGE);


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
