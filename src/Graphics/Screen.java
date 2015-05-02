package Graphics;


public class Screen {


    private int width;
    private int height;
    private int[] pixels;
    private int[] tiles;
    private static final int MAP_SIZE = 8;

    public Screen(int width, int height){
        this.width = width;
        this.height = height;
        this.pixels = new int[this.width * this.height];
        this.tiles = new int[MAP_SIZE * MAP_SIZE];

    }

    public void clear(){
        for(int i = 0; i < (this.width * this.height); i++){
            this.pixels[i] = 0;
        }
    }

    public int[] getPixels(){
        return this.pixels;
    }

    public void render(int xOffcet, int yOffcet){

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                pixels[x + y * width] = 0xff00ff;
            }
        }
    }
}
