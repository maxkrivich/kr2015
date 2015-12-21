package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Model;
import view.View;

public class Controller {
	View view;
	Model model;

	public Controller(View v, Model m) {
		view = v;
		model = m;
		this.view.addListenerForExchange(new CalculationListener());
		this.view.addListenerCourse(new Dialog());
		this.view.addListenerList(new SelectionListenner());
		this.view.addListenerForUpdate(new UpdateLis());

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

	public class UpdateLis implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (view.getInetStatus()) {
				model.updateInBank();
				view.print("Last update: " + model.getLastUpdate());
			} else
				view.print("You offline");

		}

	}

	public class Dialog implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, model.getCourses(), "Courses", JOptionPane.PLAIN_MESSAGE);
		}

	}

	public class SelectionListenner implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				model.setBank((String) view.getList().getSelectedValue());
				view.print("Last update: " + model.getLastUpdate());
				// System.out.println(view.getList().getSelectedValue());
			}

		}
	}

}
