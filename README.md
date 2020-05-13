# 目標
これはタートルグラフィックのフレームワークです。
上にある`View code`というボタンを押し、`src`→`bot`→`TurtleBot.java`へお進みください。
既に書いてあるような数行程度のコードを書いてもらい、様々な振る舞いを実装します。

# 設計モデル
MVC

# 床データの書き方
当該ファイルに`x0,y0,x1,y1,name`の順番に各床を改行しながら書き込む。
* `x0,y0` : 床の左上の点の座標
* `x1,y1` : 床の右下の点の座標
* `name` : 色の名前(`FloorColor.fromString`で検出される文字列)

# dev3 におけるカメの動作に関する手続き
`TurtleBot`や`TurtleSimulator`へのメソッド呼び出しはイベントディスパッチャスレッドとは別のスレッドで実行させること。
1. `TurtleBot`によって、`TurtleSimulator`へ動作の命令が発せられる。
2. `TurtleSimulator`は演算を実行し`Turtle`の各変数を変更したり、`World`に問い合わせてブールメソッドを処理したりする。
3. `Turtle`は内部の変数を時間を欠けてアニメーションを伴って変化させ、`TurtleListener`へ断続的にその旨を通知する。その間制御は戻さない。
4. `TurtleListener`を実装する`MainView`はその通知を受け取り次第、`Turtle`の変数に従って描画処理を行う。

* `Turtle`のメソッド呼び出し中は制御が戻らない。よって、`TurtleBot`の実行中に環境を動的に変更することができる。
* そのため、動的な環境変化に伴う`if`構文や`while`構文の振る舞いの変化を観察することができる。

# 注目ポイント(個人メモ)
### コンポーネント配置時の隙間の作り方
ボックスレイアウト適用時は`Box.createVerticalStrut`で次のコンポーネントとの距離を指定できる。
参考:`ControllerPanel.addActionButton`

ボーダーもファクトリーで作るようにする。参考:`ui.UiFactory`


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
`JButton.setMnemonic(mnemonic)`を使うとAlt+`mnemonic`で押せる。

### JDialog派生クラスコンストラクタで親Frameを取得せずに済む方法
`JOptionPane.getRootFrame`