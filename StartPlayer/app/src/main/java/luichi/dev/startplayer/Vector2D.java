package luichi.dev.startplayer;

public class Vector2D {
	private int posX;
	private int posY;
	public Vector2D(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
	Vector2D() {
		this.posX = 0;
		this.posY = 0;
	}

	int GetPosX() { return this.posX; }
	int GetPosY() { return this.posY; }
	void SetPosX(int posX){ this.posX = posX; }
	void SetPosY(int posY){ this.posY = posY; }
}
