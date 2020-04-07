# 目標
これはタートルグラフィックのフレームワークです。
上にある`View code`というボタンを押し、`src`→`bot`→`TurtleBot.java`へお進みください。
既に書いてあるような数行程度のコードを書いてもらい、様々な振る舞いを実装します。

# いかにSwing上で逐次的にアニメーションを動作させていくフレームワークを構築したか(個人メモ)
`TurtleBot`内に記述された複数のコードとSwingのビューが連動しながら順番に動作しているように見せる技術について。

Swingは描画をシングルスレッドで行う。
そのため、Swingに対しアニメーションを演出したい場合、アニメーションの処理はSwingのイベントディスパッチャスレッドとは完全に別スレッドで行う必要があり、`.join`などのスレッド終了待ちを置いてはいけない。
イベントディスパッチャスレッドが滞ると描画が行われなくなりアニメーションの演出がされなくなる。
このようなSwingの仕様に対し逐次的にアニメーションを動かす処理を担うのが`TurtleBehaviour`である。
**アニメーションのモデルをキューで管理し、それらを1つのスレッドが順番に実行していく。**

1. `TurtleBot`から`goStraight`等のメソッドが呼ばれる。
2. メソッドは、1つのアニメーションのモデルとなるオブジェクト`AnimationFrame`を生成する。
3. オブジェクトは`addAnimation`に渡され、キューに追加される。処理は`launchTurtleBotThread`に移る。
4. `launchTurtleBotThread`はアニメーション演算を行うためのスレッドを立てるが、そのスレッドは`runningTurtleBotThread`の場合直ちに動作を停止する。これは、既に別にスレッドが動作していることを示す。
**フラグを用いることで、アニメーション演算を行うためのスレッドが乱立せず、常に0か1つしかないことが保証される。**
5. アニメーションを行うためのスレッドは`runningTurtleBotThread`を立て、イベントディスパッチャスレッドとは独立して動作を開始する。
6. キューから`AnimationFrame`オブジェクトを抜き出す。
7. アニメーションが行われる直前になって、`AnimationFrame.startRunning`が呼ばれる。
**自分自身(`AnimationFrame`)が生成された時点と自分のアニメーションが行われる直前とでは、ほかのいくつかのアニメーションが動いていたりと様々な状態が異なる可能性があるので、自身のアニメーション演算に必要な定数はここで計算しなければならない。**
8. `AnimationFrame.run`が数回呼ばれる。引数`time`は`run`が呼ばれるごとに`0`から`1`に線形に増加するのでアニメーション演算に使える。
9. アニメーションの終わりに`AnimationFrame.finalFrame`が呼ばれ、1つのアニメーションが終了する。
10. キューが空になるまで6~9を繰り返す。
11. キューが空になったら`runningTurtleBotThread`を降ろし、アニメーション演算を行うためのスレッドを終了する。

このような仕組みのおかげで、`TurtleBot`から同時に複数個のメソッドが呼ばれたとしても、`TurtleBehaviour`によってアニメーションを順番に実行していくことができ、
`TurtleBot`内のコードとビューが連動しながら順番に動作しているように見せることが可能である。`TurtleBot`の処理は実際は一瞬で終わっている。

# 注目ポイント(個人メモ)
### コンポーネント配置時の隙間の作り方
ボックスレイアウト適用時は`Box.createVerticalStrut`で次のコンポーネントとの距離を指定できる。
参考:`ControllerPanel.addActionButton`

ボーダーもファクトリーで作るようにする。参考:`ui.UiFactory`

### JDialogで揮発性の意思決定ダイアログ実装
JDialogの派生クラスにおいて、
* モーダルにして
* ユーザーの意思を書き込むコンポーネントを配置し
* その内容をゲッターで取得できるようにし
* ユーザーが意思決定を承認したか棄却したか(参考:`view.PositionAndAngleSetting.wasApproved`)が分かるように

すれば、呼び出し元のコードを止めながらユーザーの意思決定を受け付けることができ、便利。`JFileChooser`のようなもの。

### コンポーネントを追加する専用メソッドを作るメリット
専用メソッドでは
* 背景色の設定
* ネストしたコンポーネントの整備
* 水平位置の設定

などを行っており、変数名が長くなりにくいのが特長。
参考:'ControllPanel'

### `ActionListener`は匿名クラスで作った方がよい。
* ボタン名の指定と作業内容のコードが近くなる
* ボタンのインスタンスをフィールドに登録する必要がなくなり、ボタンの変数名を各ボタンで使いまわしできる。

### ボックスレイアウト適用時のコンポーネントの大きさ統一
各コンポーネントの大きさ指定で`setMaximumSize`を呼び出す。参考:`ControllerPanel.addActionButton`

### JButtonのキーバインド設定
`JButton.setMnemonic(mnemonic)`を使うとAlt+`mnemonic`で押せる。参考:`view.PositionAndAngleSetting.addActionButton`

### JDialog派生クラスコンストラクタで親Frameを取得せずに済む方法
`JOptionPane.getRootFrame`