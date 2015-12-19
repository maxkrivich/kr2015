package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import model.Model;
import view.View;

public class Controller {
	View view;
	Model model;

	public Controller(View v, Model m) {
		view = v;
		model = m;
		this.view.addListenerForExchange(new CalculationListener());
		this.view.addListenerFrame(new Dialog());

	}

	public class CalculationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			double sum = view.getSum();
			if (sum == Double.POSITIVE_INFINITY)
				return;
			String from = view.getFrom(), to = view.getTo();
			double ans = model.getCalculation(sum, from, to);
			view.appendSolution(sum, ans, from, to);
		}
	}
	
	public class Dialog implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_F1){
				JOptionPane.showMessageDialog (null, model.getCourses(), "Courses", JOptionPane.PLAIN_MESSAGE);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

}
