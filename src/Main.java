import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

public class Main {

    static int N, M;
    static int[][] map;
    static int[][] isVisited;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // Input parsing with error handling
        try {
            StringTokenizer st = new StringTokenizer(bf.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());

            map = new int[N][M];
            isVisited = new int[N][M];
            Queue<int[]> que = new LinkedList<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(bf.readLine());
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    isVisited[i][j] = -1; // 방문 여부 초기화
                    if (map[i][j] == 1) { // 익은 토마토가 있는 위치를 큐에 추가
                        que.offer(new int[]{i, j});
                        isVisited[i][j] = 0; // 익은 토마토의 시작 지점
                    }
                }
            }

            bfs(que);

            int result = 0;
            boolean hasUnripe = false;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] == 0 && isVisited[i][j] == -1) { // 익지 않은 토마토가 있는 경우
                        hasUnripe = true;
                        break;
                    }
                    result = Math.max(result, isVisited[i][j]); // 최대 일수를 계산
                }
                if (hasUnripe) break;
            }

            if (hasUnripe) {
                bw.write("-1\n");
            } else {
                bw.write(result + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            bw.write("-1\n");
        } finally {
            bw.flush();
            bw.close();
            bf.close();
        }
    }

    public static void bfs(Queue<int[]> que) {
        while (!que.isEmpty()) {
            int[] start = que.poll();
            int x = start[0];
            int y = start[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= 0 && ny >= 0 && nx < N && ny < M && map[nx][ny] == 0 && isVisited[nx][ny] == -1) {
                    map[nx][ny] = 1; // 익은 토마토로 표시
                    isVisited[nx][ny] = isVisited[x][y] + 1; // 날짜 갱신
                    que.offer(new int[]{nx, ny});
                }
            }
        }
    }
}
