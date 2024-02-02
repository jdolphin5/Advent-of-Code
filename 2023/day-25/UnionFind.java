public class UnionFind {
    // data
    private int[] root;
    // Use a rank array to record the height of each vertex, i.e., the "rank" of each vertex.
    private int[] rank;
    
    public UnionFind(int n){
        this.root = new int[n];
        this.rank = new int[n]; // each rank is init to 1 since it points to itself
        
        for(int i=0;i<n;i++){
            this.root[i] = i;
            this.rank[i] = 1;
        }
    }
    public void union(int x, int y){
        int rootX = find(x);
        int rootY = find(y);
        
        if(rootY == rootX){
            return;
        }
        
        if(rank[rootY] > rank[rootX]){
            root[rootX] = rootY;
        }
        else if(rank[rootX] > rank[rootY]){
            root[rootY] = rootX;
        }
        else{
            root[rootY] = rootX;
            rank[rootY]++;
        }
    }
    public int find(int x){
        if(root[x] == x){ // root
            return x;
        }
        return root[x] = find(root[x]);
    }
    public boolean isConnected(int x, int y){
        return find(x) == find(y);
    }
}