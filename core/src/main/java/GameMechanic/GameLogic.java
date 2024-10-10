package GameMechanic;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import map.Leaf;
import models.Player;

public class GameLogic {
    private List<Rectangle> forbiddenRegions; // Список запрещённых областей
    private List<Leaf> rooms; // Список запрещённых областей
    private Player player; // Ваш игрок
    float forbiddenX ;     // X-позиция текстуры
    float forbiddenY ;     // Y-позиция текстуры
    float forbiddenWidth ; // Ширина текстуры
    float forbiddenHeight ; // Высота текстуры
    public GameLogic(Player player, List<Leaf> rooms) {
        this.player=player;
        this.rooms=rooms;
        forbiddenRegions=new ArrayList<Rectangle>();
        initializeForbiddenRegions();
    }

    private void initializeForbiddenRegions() {
        for(Leaf currentRoom:rooms){
            forbiddenX=currentRoom.room.x;
            forbiddenY=currentRoom.room.y;
            forbiddenWidth=currentRoom.room.width;
            forbiddenHeight=currentRoom.room.height;
            Rectangle forbiddenRegion = new Rectangle(forbiddenX, forbiddenY, forbiddenWidth, forbiddenHeight);
            forbiddenRegions.add(forbiddenRegion); // Добавляем в список
            for(Rectangle currentCorridor:currentRoom.halls){
                forbiddenX=currentCorridor.x;
                forbiddenY=currentCorridor.y;
                forbiddenWidth=currentCorridor.width;
                forbiddenHeight=currentCorridor.height;
                forbiddenRegions.add(forbiddenRegion);
                forbiddenRegion = new Rectangle(forbiddenX, forbiddenY, forbiddenWidth, forbiddenHeight);
                forbiddenRegions.add(forbiddenRegion); // Добавляем в список
            }
        }
    }

    public void update(float deltaTime,float moveX,float moveY) {
        // Обновляем движение игрока
        player.body.setLinearVelocity(moveX * player.speed, moveY * player.speed);

        // Проверяем, находится ли игрок в пределах проходимой области
        boolean isInNavigableRegion = false;

        for (Rectangle region : forbiddenRegions) {
            if (region.overlaps(player.getBoundingRectangle())) {
                isInNavigableRegion = true; // Игрок находится в проходимой области
                break;
            }
        }

        // Если игрок покинул все проходимые области, останавливаем его движение в этом направлении
        if (!isInNavigableRegion) {
            if (moveX > 0) { // Движение вправо
                player.body.setLinearVelocity(0, player.body.getLinearVelocity().y);
            } else if (moveX < 0) { // Движение влево
                player.body.setLinearVelocity(0, player.body.getLinearVelocity().y);
            }
            if (moveY > 0) { // Движение вверх
                player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
            } else if (moveY < 0) { // Движение вниз
                player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
            }
        }
    }
}



