package org.andrewbernier.desktopManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Menu extends JFrame implements ActionListener {

  private static ObjectMapper objectMapper = new ObjectMapper();

  // Components of the Form
  private Container c;
  private JLabel title;
  private JLabel name;
  private JTextField tname;
  private JLabel mno;
  private JTextField tmno;
  private JComboBox target;
  private JComboBox year;
  private JButton connect;

  private Map targets = null;

  public Menu() {
    try {
      URL url = Thread.currentThread().getContextClassLoader().getResource("targets.json");
      targets = objectMapper.readValue(url, Map.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    setTitle("Remote Desktop Manager");
    setBounds(300, 90, 700, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);

    c = getContentPane();
    c.setLayout(null);

    title = new JLabel("Remote Desktop Manager");
    title.setFont(new Font("Arial", Font.PLAIN, 30));
    title.setSize(600, 30);
    title.setLocation(300, 30);
    c.add(title);

    mno = new JLabel("User");
    mno.setFont(new Font("Arial", Font.PLAIN, 20));
    mno.setSize(100, 20);
    mno.setLocation(100, 150);
    c.add(mno);

    tmno = new JTextField();
    tmno.setFont(new Font("Arial", Font.PLAIN, 15));
    tmno.setSize(150, 20);
    tmno.setLocation(200, 150);
    c.add(tmno);

    mno = new JLabel("Endpoint");
    mno.setFont(new Font("Arial", Font.PLAIN, 20));
    mno.setSize(100, 20);
    mno.setLocation(100, 250);
    c.add(mno);

    target = new JComboBox(targets.keySet().toArray());
    target.setFont(new Font("Arial", Font.PLAIN, 15));
    target.setSize(150, 20);
    target.setLocation(250, 250);
    c.add(target);

    connect = new JButton("Connect");
    connect.setFont(new Font("Arial", Font.PLAIN, 15));
    connect.setSize(100, 20);
    connect.setLocation(150, 450);
    connect.addActionListener(this);
    c.add(connect);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == connect) {
      String host = String.valueOf(targets.get(target.getSelectedItem().toString()));
      String user = "";
      String cmd = String.format("MSTSC /v:%s", host);
      try {
        Runtime.getRuntime().exec(cmd);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }

    }
  }
}
