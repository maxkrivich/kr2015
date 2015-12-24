package controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.ui.RefineryUtilities;

import model.Cost;
import model.Model;
import model.PrivatBank;
import view.ChartPB;
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
		this.view.addListenerForStat(new StatListener());
		view.setUpEnd(view.getInetStatus());
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

	public class StatListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getBank().getStatStatus() && model.getBank() instanceof PrivatBank) {
				Cost[][] cc = new Cost[6][];
				try {
					cc[0] = model.getBank().readStat("statpb2014CAD.txt");
					cc[1] = model.getBank().readStat("statpb2014CHF.txt");
					cc[2] = model.getBank().readStat("statpb2014EUR.txt");
					cc[3] = model.getBank().readStat("statpb2014GBP.txt");
					cc[4] = model.getBank().readStat("statpb2014PLZ.txt");
					cc[5] = model.getBank().readStat("statpb2014USD.txt");
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						ChartPB c = new ChartPB(model.getBank().getBankName() + " stat", cc);
						c.pack();
						RefineryUtilities.centerFrameOnScreen(c);
						c.setVisible(true);
					}
				});

			}
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
				view.setEnd(model.getBank().getStatStatus());
				// System.out.println(view.getList().getSelectedValue());
			}

		}
	}

}
