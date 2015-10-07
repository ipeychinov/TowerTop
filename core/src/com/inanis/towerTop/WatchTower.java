//                   /""-._
//                  .      '-,
//                  :         '',
//                  ;      *     '.
//                  ' *         () '.
//                   \               \
//                    \      _.---.._ '.
//                     :  .' _.--''-''  \ ,'
//       .._            '/.'             . ;
//        ; `-.          ,                \'
//         ;   `,         ;              ._\
//          ;    \     _,-'                ''--._
//           :    \_,-'                          '-._
//            \ ,-'                       .          '-._
//           .'         __.-'';            \...,__       '.
//          .'      _,-'       \              \   ''--.,__ '\
//         /   _,--' ;          \             ;           "^.}
//        ;_,-' )     \  )\      )            ;
//             /       \/  \_.,-'             ;
//            /                              ;
//         ,-'  _,-'''-.    ,-.,            ;      
//      ,-' _.-'        \  /    |/'-._...--'
//     :--``             )/

package com.inanis.towerTop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inanis.towerTop.screen.MainMenuScreen;

public class WatchTower extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("font/bau93.fnt"));
		font.setColor(Color.DARK_GRAY);
		font.setScale(1.5f);
		
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		font.dispose();
	}
}
