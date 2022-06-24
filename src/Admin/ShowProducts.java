package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ShowProducts extends JFrame implements ActionListener {
   Connection con;

    {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","Project","Project");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    String cat,table;
    JButton del,Edit;
    JTable Product;
    ShowProducts(String c){
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
        back.addActionListener(E -> {new AdminMenu();dispose();});
        footer.add(back);

        del =new JButton("Delete");
        del.setForeground(Color.white);
        del.setBackground(new Color(230, 126, 34));
        del.setBounds(40,20,70,30);
        del.setFocusable(false);
        del.setFont(new Font("Didot",Font.PLAIN,12));
        del.addActionListener(this);
        footer.add(del);

        Edit =new JButton("Edit");
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
        if(e.getSource()==del){
            if (Product.getSelectedRow() != -1) {
                int opt=JOptionPane.showConfirmDialog(null,"Are u sure you want to delete?","Confirmation",JOptionPane.YES_NO_OPTION);
                if(opt==0) {
                    try {
                        Statement q = con.createStatement();
                        ResultSet r=q.executeQuery("delete from product where product_id = '"
                                +Product.getModel().getValueAt(Product.getSelectedRow(),1).toString()+"'");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null,"Deleted Sucessfully!!","You Did it",JOptionPane.INFORMATION_MESSAGE);
                    new ShowProducts(cat);
                    dispose();
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Select a Product to delete", "Error 101", JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource()==Edit){
            if(Product.getSelectedRow()!=-1) {
                new EditProduct(cat, Product.getModel().getValueAt(Product.getSelectedRow(),1).toString());
                dispose();
            }
            else
                JOptionPane.showMessageDialog(null,"Select a Product to Edit","LOL",JOptionPane.WARNING_MESSAGE);
        }
    }
}
