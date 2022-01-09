package D_Game_Engine;

public class TestGameObj extends GameObject implements Update{

	@Override
	public void update() {
		
		if (KeyInput.input == 1) {
			
			x += 0.5;
		}
		
		
	}
	
	

}
