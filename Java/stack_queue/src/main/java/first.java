public class first {
    public interface Stack {
        public int size();

        public boolean isEmpty();

        public char top();

        public char push(char o);

        public char pop();
    }

    public static class ArrayStack implements Stack {
        // ArrayStack 클래스 구현

        private char[] arr;
        private int top;
        private int capacity;

        ArrayStack(int size){
            arr = new char[size];
            capacity = size;
            top = -1;
        }

        @Override
        public int size() {
            return top+1;
        }

        @Override
        public boolean isEmpty() {
            return top==-1;
        }

        @Override
        public char top() {
            if (!isEmpty()){
                return arr[top];
            }else{
                System.out.println("top 실행불가: 스택이 비어있음(underflow)");
                return 0;
            }
        }

        @Override
        public char push(char o) {
            if (top == capacity -1){
                System.out.println("push 실행불가: 스택 참(overflow)");
                return 0;
            }
            return arr[top--];
        }

        @Override
        public char pop() {
            return this.pop();
        }
    }
    public static void main(String[] args) {
        ArrayStack sample = new ArrayStack();
        System.out.println("asdf");
        System.out.println(sample.size());
    }
}
