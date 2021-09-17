import java.util.LinkedList;

public class HaffmanMain {

    private static LinkedList<Node> list;
    private static LinkedList<Node> queue;
    private static LinkedList<Node> nodeList;

    public static void main(String[] args) {

        String wordToCompress = "effervescence";
        System.out.println(wordToCompress);

        lettersCounter(wordToCompress);
        createTree();
        createNodeList();

        String code = encode(wordToCompress);
        System.out.println(code);

        String word = decode(code);
        System.out.println(word);
    }

    //метод создает узел для каждой буквы и записывает количество ее повторений
    public static void lettersCounter(String word) {
        list = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            Node node = new Node(word.charAt(i));
            if(!list.contains(node)) {
                node.repeat = 1;
                list.add(node);
            }
            else
                list.get(list.indexOf(node)).repeat++;
        }
        list.sort(null);
    }

    //метод строит бинарное дерево из имеющихся в списке узлов
    public static void createTree() {

        while(list.size() > 1) {
            Node node1 = list.poll();
            Node node2 = list.poll();
            Node newNode = new Node();
            newNode.repeat = node1.repeat + node2.repeat;
            newNode.left = node1;
            newNode.right = node2;
            list.offer(newNode);
            list.sort(null);
        }
    }

    //создает на основе бинарного дерева - структура данных,
    // указывая родителя узла и позицию относительно родителя
    public static void createNodeList() {

        queue = new LinkedList<>();
        nodeList = new LinkedList<>();

        Node node = list.get(0);
        Node current;
        queue.add(node);

        while (true) {

            if(queue.isEmpty())
                break;

            current = queue.poll();
            nodeList.add(current);

            if(current.left != null && !queue.contains(current.left)) {
                queue.add(current.left);
                current.left.position = "left";
                current.left.parent = current;
            }
            if(current.right != null && !queue.contains(current.right)) {
                queue.add(current.right);
                current.right.position = "right";
                current.right.parent = current;
            }
        }
    }

    //здесь мы для находим бит-код для каждой буквы и записываем итоговый шифр
    public static String encode (String word) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            String temp = "";
            Node letter = null;
            for (int j = 0; j < nodeList.size(); j++) {
                if(nodeList.get(j).letter == word.charAt(i))
                    letter = nodeList.get(j);
            }
            do {
                if (letter.position == "right")
                    temp += "1";
                else if(letter.position == "left")
                    temp += "0";
                letter = letter.parent;
            }
            while (letter != null);

            temp = new StringBuilder(temp).reverse().toString();
            sb.append(temp);
        }
        return sb.toString();
    }

    //расшиврофка бит-кода обратно в слово
    public static String decode(String code) {
        Node node = list.get(0);
        StringBuilder result = new StringBuilder();
        Node nodeX;
        for (int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == '0') {
                nodeX = node.left;
            }
            else
                nodeX = node.right;

            if(nodeX.letter != 0) {
                node = list.get(0);
                result.append(nodeX.letter);

            }
            else {
                node = nodeX;
            }
        }
        return result.toString();
    }
}
