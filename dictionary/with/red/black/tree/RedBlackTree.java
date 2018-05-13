/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.with.red.black.tree;

/**
 *
 * @author HP-
 * @param <Key>
 * @param <Value>
 */
public class RedBlackTree <Key extends Comparable<? super Key>, Value>{
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    Node root;
    public class Node
    {
        Key key;
        Value val;
        Node left, right;
        Node parent;
        boolean color; // color of parent link

        private void setParent(Node parent){
            this.parent = parent;
        }
        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }

        
    }
    
    public int size(Node h){
        if(h == null) return 0;
        return Math.max(size(h.right),size(h.left))+1; 
    }
    
    private boolean isRed(Node x)
    {
        if (x == null) return false;
        return x.color == RED;
    }
    boolean isLeft(Node x)					//added isLeft for Delete
    {
        if (x.left == null) return false;
        return true ;
    }
    private Node rotateLeft(Node h)
    {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }
    private Node rotateRight(Node h)
    {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }
    private void flipColors(Node h)
    {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }
    
    public Node put(Node h, Node parent, Key key, Value val)
    {
        if (h == null) {
            Node x = new Node(key, val, RED);
            x.setParent(parent); 
            return x;
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, h, key, val);
        else if (cmp > 0) h.right = put(h.right, h, key, val);
        else if (cmp == 0) {
            System.out.println("ERROR: Word already in the dictionary!");
//            h.val = val;
        }
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
    return h;
    }
    public Node get(Key key)
    {
        Node x = root;
        while (x != null)
        {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else if (cmp == 0) return x;
        }
        return null;
    }
    
   
    
    /* Up coming functions for For Delete*/
    
    void Transplant(Node u, Node v)
    {
    	if(u.parent==null) 
    		root=v;
    	else if (u==u.parent.left)
    		u.parent.left=v;
    	else u.parent.right=v;
    }
    
    Node minTree(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }
    void delete(Node z) {
    	Node y= z,x;
    	Boolean yOrigin = y.color;
    	if(z.left==null) 
    	{
    		x=z.right;
    		Transplant(z,z.left);
    	}
    	else if(z.right==null)
    	{
    		x=z.left;
    		Transplant(z,z.left);
    	}
    	else
    	{
    		y=minTree(z.right);
    		yOrigin=y.color;
    		x=y.right;
    	}
    	if(y.parent==z)
    	{
    		x.parent=y;
    	}
    	else
    	{
            Transplant(y,y.right);
            y.right=z.right;
            System.out.println("y.right: "+y.right);
            System.out.println("y.parent: "+y.parent);
            if(y.right != null){
                y.right.parent=y;
            }
    	}
    	if(yOrigin==BLACK)
    	{
            deleteFixup(x);
    	}
    }
    void deleteFixup(Node x)
    {
    	while(x!=root && x.color==BLACK) 
    	{
    		Node w;
    		if(isLeft(x))					//isLeft add up added and parent needs to be set with value
    		{
    			w=x.parent.right;
    			if(isRed(x))
    			{
    				w.color=BLACK;
    				x.parent.color=RED;
    				rotateLeft(x.parent);
    				w=x.parent.right;
    			}
    			if(w.left.color==BLACK && w.right.color==BLACK)
    			{
    				w.color=RED;
    				x=x.parent;
    			}
    			else
    			{
    				if(w.right.color==BLACK)
    				{
    					w.left.color=BLACK;
    					w.color=RED;
    					rotateRight(w);
    					w=x.parent.right;
    				}
    				w.color=x.parent.color;
    				x.parent.color=BLACK;
    				w.right.color=BLACK;
    				rotateLeft(x.parent);
    				x=root;
    			}
    		}
    		else
    		{
    			w=x.parent.left;
    			if(isRed(w))
    			{
    				w.color=BLACK;
    				x.parent.color=RED;
    				rotateLeft(x.parent);
    				w=x.parent.left;
    			}
    			if(w.right.color==BLACK && w.left.color==BLACK)
    			{
    				w.color=RED;
    				x=x.parent;
    			}
    			else 
    			{
    				if(w.left.color==BLACK)
    				{
    					w.left.color=BLACK;
    					w.color=RED;
    					rotateLeft(w);
    					w=x.parent.left;
    				}
    				w.color=x.parent.color;
    				x.parent.color=BLACK;
    				w.left.color=BLACK;
    				rotateRight(x.parent);
    				x=root;
    			}
    		}
    	}
    	x.color=BLACK;
    }   
}
