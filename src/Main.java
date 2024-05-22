import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String[] token = in.readLine().split(" ");
        int T = Integer.parseInt(token[0]);
        int B = Integer.parseInt(token[1]);
        int L = Integer.parseInt(token[2]);
        int R = Integer.parseInt(token[3]);

        EscapeRoutes sys = new EscapeRoutes(L-1);

        for (int i = 0; i < R; i++) {
            token = in.readLine().split(" ");
            int l1 = Integer.parseInt(token[0])-1;
            int l2 = Integer.parseInt(token[1])-1;
            sys.addEdge(l1, l2);
            sys.addEdge(l2, l1);
        }

        token = in.readLine().split(" ");
        int lv = Integer.parseInt(token[0])-1;
        int ld = Integer.parseInt(token[1])-1;

        int maxGroups = sys.solve(lv, ld);
        int maxGoldBars = Math.min(maxGroups,T) * B;

        System.out.println(maxGoldBars);
    }
}