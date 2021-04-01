import java.lang.Math;
import java.util.Random;

public class Closest {
    public static void Sort(int[][] A, int length, int xy){
        for(int i=0; i<length-1; i++){
            for(int j=i+1; j<length; j++){
                if(A[i][xy]>A[j][xy]){
                    int[][] temp = new int[1][2];
                    temp[0] = A[i];
                    A[i] = A[j];
                    A[j] = temp[0];
                }
            }
        }
    }
    public static double Distance(int[][] S, int a, int b){
        return Math.sqrt(Math.pow(S[a][0]-S[b][0],2)+Math.pow(S[a][1]-S[b][1],2));
    }
    public static int Mindistance(double d1, double d2, double d3){
        if(d1<d2){
            if(d1<d3) return 1;
            else return 3;
        }
        else{
            if(d2<d3) return 2;
            else return 3;
        }
    }

    public static int[][] ClosestPair(int[][] S) {
        int[][] C = new int[2][2];
        if (S.length<=3){
            if (S.length==2) {
                return S;
            }
            double distance1 = Distance(S, 0, 1);
            double distance2 = Distance(S, 0, 2);
            double distance3 = Distance(S, 1, 2);
            int mind = Mindistance(distance1, distance2, distance3);
            switch (mind) {
                case 1 -> {
                    C[0] = S[0];
                    C[1] = S[1];
                }
                case 2 -> {
                    C[0] = S[0];
                    C[1] = S[2];
                }
                default -> {
                    C[0] = S[1];
                    C[1] = S[2];
                }
            }
        }
        else {
            int mid = (S.length + 1) / 2;
            int[][] SL = new int[mid][2];
            int[][] SR = new int[S.length - mid][2];
            for (int i = 0; i < mid; i++) {
                SL[i] = S[i];
            }
            for (int j = mid; j < S.length; j++) {
                SR[j - mid] = S[j];
            }
            int[][] CPL = ClosestPair(SL);
            int[][] CPR = ClosestPair(SR);

            double d = Math.min(Distance(CPL, 0, 1), Distance(CPR, 0, 1));
            int[][] SC = new int[S.length][2];
            int scl = 0;
            for (int i = 0; i < SL.length; i++) {
                if (SL[SL.length - 1][0] - d <= SL[i][0]) {
                    SC[scl] = SL[i];
                    scl++;
                }
            }
            for (int j = 0; j < SR.length; j++) {
                if (SR[0][0] + d >= SR[j][0]) {
                    SC[scl] = SR[j];
                    scl++;
                }
            }
            Closest.Sort(SC, scl,1);
            int[][] CPC = new int[2][2];
            CPC[0] = SC[0];
            CPC[1] = SC[1];
            double scmin = Distance(SC, 0, 1);
            for (int i = 0; i < scl - 1; i++) {
                for (int j = i + 1; j < scl; j++) {
                    if (SC[i][1] - SC[j][1] <= d) {
                        double distance = Distance(SC, i, j);
                        if (distance < scmin) {
                            scmin = distance;
                            CPC[0] = SC[i];
                            CPC[1] = SC[j];
                        }
                    }
                }
            }
            int mind = Mindistance(Distance(CPL, 0, 1), Distance(CPR, 0, 1), Distance(CPC, 0, 1));
            switch (mind) {
                case 1 -> {
                    C[0] = CPL[0];
                    C[1] = CPL[1];
                }
                case 2 -> {
                    C[0] = CPR[0];
                    C[1] = CPR[1];
                }
                default -> {
                    C[0] = CPC[0];
                    C[1] = CPC[1];
                }
            }
        }
        return C;
    }

    public static void main(String[] args) {
        Random r = new Random();
        int[][] S = new int[r.nextInt(30)][2];
        for(int i=0; i<S.length; i++){
            for(int j=0; j<2; j++){
                S[i][j] = r.nextInt(100);
            }
        } //int[][] S={{3,2}, {4,3}, {5,2}, {6,7}, {8,4}};
        Closest closest = new Closest();
        closest.Sort(S,S.length,0);
        System.out.print("S:{");
        System.out.printf("{%d, %d}",S[0][0],S[0][1]);
        for(int i=1; i<S.length; i++){
            System.out.printf(", {%d, %d}",S[i][0],S[i][1]);
        }
        System.out.println("}");
        int[][] a = closest.ClosestPair(S);
        System.out.printf("최근접 점의 쌍은 (%d, %d), (%d, %d)이고, ",a[0][0],a[0][1], a[1][0], a[1][1]);
        System.out.printf("최근접 점의 쌍의 거리는 %.2f이다.",closest.Distance(a, 0, 1));
    }
}