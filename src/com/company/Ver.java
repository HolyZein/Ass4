package com.company;
import java.util.*;
import java.util.stream.Stream;


    class Ver<V>{
        private final V data;
        private final Map<Ver<V>, Double> adj = new HashMap<>();

        public Ver(V info) {
            this.data = info;
        }

        public void addAdjVer(Ver<V> dest, double weigh){
            adj.put(dest, weigh);
        }

        public Map<Ver<V>, Double> getAdj() {
            return adj;
        }

        public V getData() {
            return data;
        }
    }

    class BreadthFirstFind<T> extends Find<T> {

        public BreadthFirstFind(weiGraph<T> gr, T sou) {
            super(new Ver<>(sou));
            bfs(gr, sou);
        }

        private void bfs(weiGraph<T> gr, T cur) {
            flagged.add(new Ver<>(cur));
            Queue<Ver<T>> q = new LinkedList<>();
            q.add(new Ver<>(cur));
            if (!q.isEmpty()) do {
                Ver<T> tVer = q.remove();
                gr.verMap.get(gr.verMap.indexOf(tVer)).getAdj().keySet().stream().filter(ver -> !flagged.contains(ver)).forEach(ver -> {
                    flagged.add(ver);
                    verMap.put(ver, tVer);
                    q.add(ver);
                });
            } while (!q.isEmpty());
        }
    }

    class DepthFirstFind<T> extends Find<T> {

        public DepthFirstFind(weiGraph<T> gr, T sou) {
            super(new Ver<>(sou));
            dfs(gr, sou);
        }

        private void dfs(weiGraph<T> gr, T curr) {
            flagged.add(new Ver<>(curr));
            counter++;

            Ver<T> cur = new Ver<>(curr);
            gr.verMap.get(gr.verMap.indexOf(cur)).getAdj().keySet().stream().filter(ver -> !flagged.contains(ver)).forEach(ver -> {
                verMap.put(ver, new Ver<>(curr));
                dfs(gr, ver.getData());
            });
        }

    }

    class Find<T> {
        protected int counter;
        protected Set<Ver<T>> flagged;
        protected Map<Ver<T>, Ver<T>> verMap;
        protected final Ver<T> sou;

        public Find(Ver<T> sou) {
            this.sou = sou;
            flagged = new HashSet<>();
            verMap = new HashMap<>();
        }

        public boolean ifHas(Ver<T> v) {
            return flagged.contains(v);
        }

        public Iterable<Ver<T>> path(T v) {
            if (!ifHas(new Ver<>(v))) return null;
            LinkedList<Ver<T>> list = new LinkedList<>();
            Stream.iterate(new Ver<>(v), i -> i != sou, i -> verMap.get(i)).forEach(list::push);
            list.push(sou);

            return list;
        }

        public int getCounter() {
            return counter;
        }
    }

    class weiGraph<T> {
        private final boolean undir;
        public ArrayList<Ver<T>> verMap = new ArrayList<>();
        public weiGraph() {
            this.undir = true;
        }

        public weiGraph(boolean undir) {
            this.undir = undir;
        }

        public void VerAdd(T ver) {
            verMap.add(new Ver<>(ver));
        }

        public void EdgeAdd(T sou, T dest, double wei) {
            if (Ver0(sou)) {
                VerAdd(sou);
            }
            if (Ver0(dest))
                VerAdd(dest);

            if (Edge0(sou, dest)
                    || sou.equals(dest) || !verMap.contains(new Ver<>(sou))) return;

            verMap.get(verMap.indexOf(new Ver<>(sou))).addAdjVer(new Ver<>(dest), wei);


            if (undir)
                verMap.get(verMap.indexOf(new Ver<>(dest))).addAdjVer(new Ver<>(sou), wei);
        }

        public int getVerCount() {
            return verMap.size();
        }

        public int getEdgeCount() {
            int counter = 0;

            for (Ver<T> to : verMap) {
                counter += to.getAdj().size();
            }

            if (undir)counter /= 2;

            return counter;
        }


        public boolean Ver0(T v) {
            if (!verMap.contains(new Ver<>(v))) return true;
            else return false;
        }

        public boolean Edge0(T source, T dest) {
            return !Ver0(source) && verMap.get(verMap.indexOf(new Ver<>(source))).getAdj().containsKey(new Ver<>(dest));
        }
    }



    public class Main {




        public static void main(String[] args) {
            weiGraph<String> graph = new weiGraph<>(true);



            graph.EdgeAdd("Almaty", "Astana", 2.1);
            graph.EdgeAdd("Almaty", "Shymkent", 7.2);
            graph.EdgeAdd("Shymkent", "Astana", 3.9);
            graph.EdgeAdd("Astana", "Kostanay", 3.5);
            graph.EdgeAdd("Shymkent", "Kyzylorda", 5.4);

//        System.out.println("Dijkstra:");
//        Find<String> djk = new DijkstraSearch<>(graph, "Almaty");
//        outputPath(djk, "Kyzylorda");

//        System.out.println("DFS:");
//        Search<String> dfs = new DepthFirstSearch<>(graph, "Almaty");
//        outputPath(dfs, "Kyzylorda");
//
//        System.out.println("\n--------------------------------");
//
//        System.out.println("BFS:");
//        Search<String> bfs = new BreadthFirstSearch<>(graph, "Almaty");
//        outputPath(bfs, "Kyzylorda");
        }

        public static void outputPath(Find<String> search, String key) {
            for (Ver<String> v : search.path(key)) {
                System.out.print(v + " -> ");
            }
        }
    }



