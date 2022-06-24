package Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManageStock extends JFrame implements ActionListener {
    Connection con;

    {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Project","Project");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    String cat;
    JButton Edit;
    JTable Product;
    String username,table;
    ManageStock(String c,String str){
        username=str;
        cat=c;
        setTitle("Product");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(700,600);
        setLayout(null);
        setLocationRelativeTo(null);
        ImageIcon mainlogo=new ImageIcon("images\\mainlogo.png");
        setIconImage(mainlogo.getImage());

        JPanel head=new JPanel();
        head.setBounds(0,0,700,100);
        head.setBackground(new Color(44, 62, 80));
        head.setLayout(null);
        JLabel heading= new JLabel(cat);
        heading.setFont(new Font("Futura",Font.BOLD,35));
        heading.setBounds(30,25,400,50);
        heading.setForeground(Color.white);
        head.add(heading);

        String[] col={"No.","ID","Name","Price(Rs.)","Quantity"};

        DefaultTableModel Tb=new DefaultTableModel(col,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 4) {
                    return true;
                }
                return false;
            }
        };
        Product=new JTable(Tb);
        table = switch (cat) {
            case "Beverages" -> "Beverage";
            case "Snacks" -> "snack";
            case "Bakery Items" -> "Bakery_item";
            default -> "fruit";
        };
        try {
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery("select * from "+table);
            int no = 1;
            while(rs.next()) {
                Object[] o = {no++, rs.getString(1) , rs.getString(2), rs.getString(3), rs.getString(5)};
                Tb.addRow(o);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        Product.setFont(new Font("Futura",Font.PLAIN,15));
        Product.setRowHeight(30);
        TableColumnModel columnModel = Product.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(3).setPreferredWidth(95);
        columnModel.getColumn(4).setPreferredWidth(97);



        JScrollPane body=new JScrollPane(Product);
        body.setBounds(1,100,687,400);
        body.setBackground(new Color(234, 250, 241 ));


        JPanel footer=new JPanel();
        footer.setBounds(0,500,700,400);
        footer.setBackground(new Color(93, 109, 126));
        footer.setLayout(null);

        JButton back =new JButton("Back");
        back.setForeground(Color.white);
        back.setBackground(new Color(203, 67, 53));
        back.setBounds(600,20,60,30);
        back.setFocusable(false);
        back.setFont(new Font("Tahoma",Font.PLAIN,12));
        back.addActionListener(E -> {new EmployeeMenu(username);dispose();});
        footer.add(back);

        Edit =new JButton("Update");
        Edit.setForeground(Color.white);
        Edit.setBackground(new Color(22, 160, 133));
        Edit.setBounds(300,10,100,40);
        Edit.setFocusable(false);
        Edit.setFont(new Font("Didot",Font.BOLD,16));
        Edit.addActionListener(this);
        footer.add(Edit);


        setVisible(true);
        add(head);
        add(body);
        add(footer);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Edit){
            int rows=0;
            boolean flag=true;
            try {
                Statement q = con.createStatement();
                ResultSet rs = q.executeQuery("select count(*) from "+table);
                if(rs.next())
                    rows=rs.getInt(1);
                for (int i = 0; i < rows; i++) {
                    try {
                        int Stock = Integer.parseInt(String.valueOf(Product.getModel().getValueAt(i, 4)));
                        try {
                            PreparedStatement ps = con.prepareStatement("update "+table+" set stock=? "
                                    +"where product_id = ?");
                            ps.setInt(1,Stock);
                            ps.setString(2,String.valueOf(Product.getModel().getValueAt(i, 1)));

                            ps.executeUpdate();
                        }
                        catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } catch (NumberFormatException Num) {
                        JOptionPane.showMessageDialog(null, "Enter a Number in row " + (i + 1), "Error 101", JOptionPane.ERROR_MESSAGE);
                        flag = false;
                    }
                }
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
            if(flag){
                JOptionPane.showMessageDialog(null,"Stocks Updated Sucesfully!","Add More Cocomo",JOptionPane.INFORMATION_MESSAGE);
                new ManageStock(cat,username);
                dispose();
            }
        }
    }
}
