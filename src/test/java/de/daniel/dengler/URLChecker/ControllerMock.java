package de.daniel.dengler.URLChecker;

public class ControllerMock extends Controller {

	public ControllerMock(MainWindow me) {
		super(me);
	}
	
	@Override
	protected void append(String string) {
		System.out.println(string);
	}
	
	@Override
	protected void setProgressMaximum(int size) {
		System.out.println("ProgressMax: "+ size);
	}
	
	@Override
	void setProgressBarValue(int i) {
		// TODO Auto-generated method stub
		System.out.println("ProgressValue set to: "+ i);
	}

}
