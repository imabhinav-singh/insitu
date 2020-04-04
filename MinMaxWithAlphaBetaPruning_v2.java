import java.util.*;
public class MinMaxWithAlphaBetaPruning_v2{

    static int action;

    public boolean terminal_test(int state){
        if(state == 1 || state == 0) return true;
        else return false;
    }

    public int utility(String player, int state){
        if(player.equals("max")){
            if(state == 1) return -1;
            else return 1;
        }
        else {
            if(state == 1) return 1;
            else return -1;            
        }
    }

    public int result(int state, int action){
        return state - action;
    }

    public int max_value(int state, int alpha, int beta){

        if(terminal_test(state)){
            return utility("max", state);
        }
        int v = Integer.MIN_VALUE;
        for(int i=3; i>=1; i--){
            if(i>state)continue;
            int x = min_value(result(state, i), alpha, beta);
            if(v < x){
                v = x;
                action = i;
            }

            if(v == 1){
                return v;
            }

            if (v >= beta) {
                return v;
            }
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    public int min_value(int state, int alpha, int beta){

        if(terminal_test(state)){
            return utility("min", state);
        }
        int v = Integer.MAX_VALUE;
        for(int i=3; i>=1; i--){
            if(i>state)continue;
            v = Math.min(v, max_value(result(state, i), alpha, beta));

            if(v == -1){
                return v;
            }
            
            if (v <= alpha) {
                return v;
            }
            beta = Math.min(beta, v);
        }
        return v;
    }

    public int alpha_beta_search(int state){
        max_value(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return action;
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n, move;
        MinMaxWithAlphaBetaPruning_v2 obj = new MinMaxWithAlphaBetaPruning_v2();

        System.out.println("Enter the number of sticks");
        n = sc.nextInt();


        String player = "max";
        
        while(!obj.terminal_test(n)){
            move = obj.alpha_beta_search(n);
            System.out.println("Player 1: I pick "+move+" sticks. \n"+(n-move)+" sticks left.");
            n -= move;

            player = "min";

            if(obj.terminal_test(n)) break;

            move = obj.alpha_beta_search(n);
            System.out.println("Player 2: I pick "+move+" sticks. \n"+(n-move)+" sticks left.");
            n -= move;            

            player = "max";
        }

        if(obj.utility(player, n) == -1) System.out.println("Player 2 won");
        else System.out.println("Player 1 won");
        sc.close();
    }
}