package Fgnb;
import java.text.SimpleDateFormat;
import java.util.*;
public class DFS {
    static int nn = 1000;
    static int vis[] = new int [nn];
    static int a[] = new int [nn];
    static int Mac[] = new int [nn];
    static int Ans[][] = new int [nn][nn];
    static int Rns[][] = new int [nn][nn];
    static int Bns[][] = new int [nn][nn];
    static int n,m,k;
    static int tot;
    static int mins;
    static Mission[][] work = new Mission[nn][nn];
    static Mission[] ans = new Mission[nn];
    static Mission[] tmp = new Mission[nn];
    static String Ass[][] = new String [nn][nn];
    static String Rss[][] = new String [nn][nn];
    public static void init(){//��տռ�
        tot = 0;
        mins = 2000;
        for(int i=0 ; i<nn ; i++){
            for(int j=0 ; j<nn; j++){
                work[i][j] = new Mission();
            }
        }
        for(int i=0 ;i<nn ; i++){
            ans[i] = new Mission();
        }
        for(int i=0 ;i<nn ; i++){
            tmp[i] = new Mission();
        }
    }
    public static void dfss(int step, int time){//���ѵ���
        if(time>mins) return;
        if(step==tot){
            if(time<=mins){
                mins=time;
                for(int i=0;i<tot;i++){
                    ans[i].Sta = tmp[i].Sta;
                    ans[i].End = tmp[i].End;
                    ans[i].macc  = tmp[i].macc;
                    ans[i].no = tmp[i].no;
                }
            }
            return;
        }
        for(int i=0;i<n;i++){
            int j = vis[i];
            if(j == a[i]) continue;
            if(j-1<0){
                int Begin = Math.max(Mac[work[i][j].mac], 0);
                int tmpp = Mac[work[i][j].mac];
                Mac[work[i][j].mac] = Begin+work[i][j].len;
                int ntime = Math.max(time, Mac[work[i][j].mac]);
                work[i][j].ed = ntime;
                tmp[step].Sta = Begin;
                tmp[step].End = Mac[work[i][j].mac];
                tmp[step].macc = work[i][j].mac;
                tmp[step].no = i;
                vis[i]++;
                dfss(step+1, ntime);
                vis[i]--;
                Mac[work[i][j].mac] = tmpp;
            }
            else{
                int Begin = Math.max(Mac[work[i][j].mac], work[i][j-1].ed);
                int tmpp = Mac[work[i][j].mac];
                Mac[work[i][j].mac] = Begin+work[i][j].len;
                int ntime = Math.max(time, Mac[work[i][j].mac]);
                work[i][j].ed = ntime;
                tmp[step].Sta = Begin;
                tmp[step].End = Mac[work[i][j].mac];
                tmp[step].macc = work[i][j].mac;
                tmp[step].no = i;
                vis[i]++;
                dfss(step+1, ntime);
                vis[i]--;
                Mac[work[i][j].mac] = tmpp;
            }
        }
    }

    /**
     * DFS.dfs(funmins)���Ѽ��㹤����������Сʱ���4���������ĸ���ͼ����
     * @param FunMins ���빤������
     * @return mins ����������Сʱ��mins
     */
    public int dfs(Workorder FunMins){
        new DFS().init();
        n = FunMins.Work_number;
        m = FunMins.Mac_number;
        for(int i=0 ;i<n ; i++){
            Workpiece q = FunMins.PieceList.get(i);
            k = q.Work_piece;
            a[i] = k;
            tot += k;
            for(int j=0; j<k; j++){
                Sequence qq = q.SeqList.get(j);
                work[i][j].mac = qq.mac;
                work[i][j].len = qq.len;
                work[i][j].ed = -1;
            }
        }
        new DFS().dfss(0,0);
        for(int i = 0; i < tot; i++) {//����Ans
            for(int j = ans[i].Sta; j < ans[i].End; j++) {
                Ans[ans[i].macc][j] = ans[i].no+1;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {//����Ans��Ӧ����ʱ��Ass����
            for(int i=0;i<FunMins.Mac_number;i++){
                for(int j=0 ;j<mins ;j++){
                    if(Ans[i][j]==0){
                        continue;
                    }
                    String str = FunMins.StartTime;
                    Date dt = sdf.parse(str);
                    Calendar js = Calendar.getInstance();
                    js.setTime(dt);
                    js.add(Calendar.DAY_OF_YEAR,j+1);
                    Date dt1 = js.getTime();
                    String restr = sdf.format(dt1);
                    Ass[i][j] = restr;
                }
            }
        }
        catch (Exception e){
        }
        //---------------------------------------------------------------------------
        for(int i = 0; i < m; i++) {// ����Rns
            for(int j = 0; j < mins; j++) {
                if(Ans[i][j] == 0)
                    continue;
                else {
                    Rns[Ans[i][j]][j] = i+1;
                }
            }
        }
        SimpleDateFormat rdf = new SimpleDateFormat("yyyy-MM-dd");
        try {//����Rns��Ӧ����ʱ��Rss����
            for(int i=1;i<=FunMins.Work_number;i++){
                for(int j=0 ;j<mins ;j++){
                    if(Rns[i][j]==0){
                        continue;
                    }
                    String str = FunMins.StartTime;
                    Date dtt = rdf.parse(str);
                    Calendar jss = Calendar.getInstance();
                    jss.setTime(dtt);
                    jss.add(Calendar.DAY_OF_YEAR,j+1);
                    Date dt11 = jss.getTime();
                    String restr = rdf.format(dt11);
                    Rss[i][j] = restr;
                }
            }
        }
        catch (Exception e){
        }
        return mins;
    }

    /**
     * �õ�����-ʱ�����ͼ�������ݣ�����Ans����
     * @return Ans (����-ʱ��)����
     */
    public int[][] getAns(){
        return Ans;
    }

    /**
     * �õ�����-ʱ�����ͼ�������ݣ�����Rns����
     * @return Rns (����-ʱ��)����
     */
    public int[][] getRns(){
        return Rns;
    }

    /**
     * �õ�����-ʱ�����ͼ��ɫ����ʱ�䣬����Ass�ַ�����
     * @return Ass (����-ʱ��)�ַ�����
     */
    public String[][] getAss(){return Ass;};

    /**
     * �õ�����-ʱ�����ͼ��ɫ����ʱ�䣬����Rss�ַ�����
     * @return Rss (����-ʱ��)�ַ�����
     */
    public String[][] getRss(){return Rss;};


}
