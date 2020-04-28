# 目標
これはタートルグラフィックのフレームワークです。
上にある`View code`というボタンを押し、`src`→`bot`→`TurtleBot.java`へお進みください。
既に書いてあるような数行程度のコードを書いてもらい、様々な振る舞いを実装します。

# 設計モデル
MVC

# dev1 におけるカメの動作に関する手続き
1. `TurtleBot`によって、`TurtleSimulator`へ動作の命令が発せられる。
2. `TurtleSimulator`は演算を実行し`Turtle`の各変数を変更したり、`World`に問い合わせてブールメソッドを処理したりする。
3. `Turtle`は内部の変数を直ちに変更し、`TurtleListener`へ前回の変数と今回の変数を通知する。
4. `TurtleListener`を実装する`TurtleViewModel`は、自身の各変数がアニメーションを伴って変移できるよう、`AnimationFrame`を生成する。
5. `TurtleViewModel`は、`AnimationFrame`を別スレッドで順番に実行していく。アニメーションが行われるたびに`TurtleViewModelListener`へ通知する。
6. `TurtleViewModelListener`を実装する`MainView`は、`TurtleViewModel`の各変数を基にカメを描画する。

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