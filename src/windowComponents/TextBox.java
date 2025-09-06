package windowComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;

import deviceInputs.KeyHandler;
import windows.SignInPanel;
import windows.SignUpPanel;

/**
 * The {@code TextBox} class is a component that allows the
 * editing of a single line of text. It also keeps track of
 * its place holder.
 * 
 * <p>It implements {@link javax.swing.JTextField} functionality.</p>
 * 
 * @author Edward Assaf
 * @author Batoul Khaleel
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TextBox extends JTextField {
	/**
	 * The place holder of the text box.
	 */
	private String placeHolder;
 
	/**
	 * The panel that this object belongs to.
	 */
	private JPanel currentPanel;
	
	/**
	 * This {@code String} tracks what step of the panel's
	 * steps this text box belongs to.
	 */
	private String currentStep;
  
	/**
	 * This constructor constructs the text box, and adds
	 * a default mouse listener that clears the text box
	 * whenever it's pressed and the displayed text is
	 * the place holder.
	 * 
	 * @param placeHolder  The place holder.
   	 */
	public TextBox(String placeHolder, String currentStep, JPanel currentPanel) {
		// Initializations.
		this.placeHolder = placeHolder;
		this.currentStep = currentStep;
		this.currentPanel = currentPanel;
		setText(placeHolder);
		
		// Adding mouse listener.
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if (getText().equals(placeHolder)) {
					setText("");
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		
		// Adding key-binding for the 'Enter' key.
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionOfEnter();
			}
		});
		
		// Adding the key-binding for the 'Escape' key.
		Action escapeAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionOfEscape();
			}
		};
		getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escapePressed");
		getActionMap().put("escapePressed", escapeAction);
		
		// Adding the key-binding for the 'F11' key.
		Action f11Action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionOfF11();
			}
		};
		getInputMap().put(KeyStroke.getKeyStroke("F11"), "f11Pressed");
		getActionMap().put("f11Pressed", f11Action);
	}
  
	/**
	 * This function defines the action that should occur
	 * when 'Enter' key is pressed while the focus is given
	 * to a text box.
	 */
	private void actionOfEnter() {
		if (currentPanel instanceof SignUpPanel) {
			SignUpPanel currentSignUpPanel = (SignUpPanel) currentPanel;
			if (currentStep.equals("username")) {
				currentSignUpPanel.usernameBox_nextButton.doClick();
			}
			if (currentStep.equals("password")) {
				currentSignUpPanel.passwordBox_nextButton.doClick();
			}
			if (currentStep.equals("verifyPassword")) {
				currentSignUpPanel.verifyPasswordBox_nextButton.doClick();
			}
		}
		else if (currentPanel instanceof SignInPanel) {
			SignInPanel currentSignInPanel = (SignInPanel) currentPanel;
			currentSignInPanel.signInButton.doClick();
		}
	}
	
	/**
	 * This function defines the action that should occur
	 * when 'Escape' key is pressed while the focus is given
	 * to a text box.
	 */
	private void actionOfEscape() {
		if (currentPanel instanceof SignUpPanel) {
			SignUpPanel currentSignUpPanel = (SignUpPanel) currentPanel;
			if (currentStep.equals("username")) {
				currentSignUpPanel.usernameBox_backButton.doClick();
			}
			if (currentStep.equals("password")) {
				currentSignUpPanel.passwordBox_backButton.doClick();
			}
			if (currentStep.equals("verifyPassword")) {
				currentSignUpPanel.verifyPasswordBox_backButton.doClick();
			}
		}
		else if (currentPanel instanceof SignInPanel) {
			SignInPanel currentSignInPanel = (SignInPanel) currentPanel;
			currentSignInPanel.backButton.doClick();
		}
	}
	
	/**
	 * This function defines the action that should occur
	 * when 'F11' key is pressed while the focus is given
	 * to a text box.
	 */
	private void actionOfF11() {
		if (currentPanel instanceof SignUpPanel) {
			SignUpPanel currentSignUpPanel = (SignUpPanel)currentPanel;
			new KeyHandler(currentSignUpPanel.window).fullScreen.performAction();
		}
		else if (currentPanel instanceof SignInPanel) {
			SignInPanel currentSignInPanel = (SignInPanel) currentPanel;
			new KeyHandler(currentSignInPanel.window).fullScreen.performAction();
		}
	}
	
	/**
	 * The getter of the {@link #placeHolder}.
	 * 
	 * @return The place holder.
	 */
	public String getPlaceHolder() {
		return placeHolder;
	}
	
}