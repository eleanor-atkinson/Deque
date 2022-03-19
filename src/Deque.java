import tester.Tester;
import java.util.function.Predicate;

// to represent a generic list in such a way that we can start either at the front, or at the back, 
// and move through the list in either direction
class Deque<T> {
  Sentinel<T> header;

  //to initialize the field of Deque to a new sentinel
  Deque() {
    this.header = new Sentinel<T>();
  }

  // takes a particular Sentinel value to use
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // counts the number of nodes in a list Deque
  int size() {
    return header.countNodes();
  }

  // consumes a value of type T and inserts it at the front of the list
  void addAtHead(T t) {
    this.header.addAtHead(t);
  }

  // consumes a value of type T and inserts it at the tail of this list
  void addAtTail(T t) {
    this.header.addAtTail(t);
  }

  // removes the first node from this Deque 
  T removeFromHead() {
    return this.header.removeFromHead();
  }

  // removes the last node from this Deque
  T removeFromTail() {
    return this.header.removeFromTail();
  }

  // produces the first node in this Deque for which the given predicate returns true
  ANode<T> find(Predicate<T> pred) {
    return this.header.find(pred);
  }

  // removes the given node from this Deque
  void removeNode(ANode<T> node) {
    node.removeHelp();
  }
}

// to represent a node in the Deque
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // count the number of nodes in a node or sentential 
  abstract int countNodes();

  // is this ANode a sentinel?
  abstract boolean isSentinel();

  // removes a node from this Deque
  abstract T removeNode();

  // returns the data found at a node
  abstract T findData();

  // returns the prev Node;
  abstract ANode<T> findPrev();

  // returns the next Node;
  abstract ANode<T> findNext();

  // applies the predicate to this ANode and returns the ANode if true 
  abstract ANode<T> findHelp(Predicate<T> pred);

  // removes the node from the ANode
  abstract void removeHelp();

}

// to represent a node in the Deque
class Node<T> extends ANode<T> {
  T data;

  // initializes the data field and initializes next and prev to null
  Node(T data) {
    this.data = data; 
    this.next = null;
    this.prev = null;
  }

  // to update the nodes
  Node(T data, ANode<T> next, ANode<T> prev) {
    this(data);
    this.next = next;
    this.prev = prev;
    if (next == null || prev == null) {
      throw new IllegalArgumentException("one or more of the given Nodes is null");
    }
    else {
      next.prev = this;
      prev.next = this;
    }
  }

  // count the number of nodes in a node or sentential 
  int countNodes() {
    if (this.next.isSentinel()) {
      return 1;
    }
    else {
      return this.next.countNodes() + 1;
    }
  }

  // is this Node a sentinel?
  boolean isSentinel() {
    return false;
  }

  // removes this Node from the Deque 
  T removeNode() {
    prev.next = this.next;
    next.prev = this.prev;
    return this.data;
  }

  // returns the data stored at a node
  T findData() {
    return this.data;
  }

  //returns the prev Node;
  ANode<T> findPrev() {
    return this.prev;
  }

  //returns the next Node;
  ANode<T> findNext() {
    return this.next;
  }


  // applies the predicate to this Node and returns the Node if true 
  ANode<T> findHelp(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.findHelp(pred);
    }
  }

  // removes this Node from the Deque
  void removeHelp() {
    this.removeNode();

  }
}

// to represent an empty node in the Deque
class Sentinel<T> extends ANode<T> {

  // initializes the next and prev fields of the Sentinel to the Sentinel itself
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // count the number of nodes in a node or sentential 
  int countNodes() {
    if (this.next == null || this.next.isSentinel()) {
      return 0;
    }
    else {
      return this.next.countNodes();
    }
  }

  // is this ANode a sentinel?
  boolean isSentinel() {
    return true;
  }

  //consumes a value of type T and inserts it at the front of the list
  void addAtHead(T t) {
    new Node<T>(t, this.next, this);
  }

  // consumes a value of type T and inserts it at the tail of this list
  void addAtTail(T t) {
    new Node<T>(t, this, this.prev);
  }

  // removes the first node from this Deque
  T removeFromHead() {
    return this.next.removeNode();
  }

  // removes a node from this Deque
  T removeNode() {
    throw new RuntimeException("Can't remove a node from an empty list");
  }

  // removes the last node from this Deque
  T removeFromTail() {
    return this.prev.removeNode();
  }

  //returns the data stored at a node
  T findData() {
    throw new RuntimeException("Can't return data from an empty list");
  }

  //returns the prev Node;
  ANode<T> findPrev() {
    return this.next;
  }

  //returns the next Node;
  ANode<T> findNext() {
    return this.next;
  }

  // produces the first node in this Deque for which the given predicate returns true
  ANode<T> find(Predicate<T> pred) {
    return this.next.findHelp(pred);
  }

  // applies the predicate to this Sentinel and returns the Sentinel if true 
  ANode<T> findHelp(Predicate<T> pred) {
    return this;
  }

  // returns nothing 
  void removeHelp() {
    return;
  }
}


// to represent a class that tests if this string is the same string as the given string
class SameString implements Predicate<String> {
  String s;

  // to initialize the field of same string
  SameString(String s) {
    this.s = s;
  }

  //is this string the same as the given string?
  public boolean test(String string) {
    return s.equals(string);
  }
}

// to represent a class that tests if this integer is the same integer as the given integer
class SameInteger implements Predicate<Integer> {
  int i;

  // to initialize the field of same integer
  SameInteger(int i) {
    this.i = i;
  }

  // is this integer the same as the given integer?
  public boolean test(Integer integer) {
    return i == integer;
  }
}

// to represent examples and tests of Deques
class ExamplesDeque {

  Sentinel<String> sentstring;
  Sentinel<Integer> sentinteger;
  Sentinel<String> selfreferential;

  ANode<String> node1;
  ANode<String> node2;
  ANode<String> node3;
  ANode<String> node4;

  ANode<Integer> node1int;
  ANode<Integer> node2int;
  ANode<Integer> node3int;
  ANode<Integer> node4int;
  ANode<Integer> node5int;

  Deque<String> deque1;
  Deque<String> deque2;
  Deque<Integer> deque3;
  Deque<String> deque4;

  Predicate<String> containscde = new SameString("cde");
  Predicate<String> containsxyz = new SameString("xyz");
  Predicate<Integer> contains1 = new SameInteger(1);

  // Initialize the data
  void initializeData() {

    sentstring = new Sentinel<String>();
    node1 = new Node<String>("abc", sentstring, sentstring);
    node2 = new Node<String>("bcd", sentstring, node1);
    node3 = new Node<String>("cde", sentstring, node2);
    node4 = new Node<String>("def", sentstring, node3);

    sentinteger = new Sentinel<Integer>();
    node1int = new Node<Integer>(4, sentinteger, sentinteger);
    node2int = new Node<Integer>(3, sentinteger, node1int);
    node3int = new Node<Integer>(2, sentinteger, node2int);
    node4int = new Node<Integer>(1, sentinteger, node3int);
    node5int = new Node<Integer>(0, sentinteger, node4int);

    deque1 = new Deque<String>();
    deque2 = new Deque<String>(sentstring);
    deque3 = new Deque<Integer>(sentinteger);

    selfreferential = new Sentinel<String>();
    deque4 = new Deque<String>(selfreferential);
  }

  // testing the size method
  void testSize(Tester t) {
    this.initializeData();
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque3.size(), 5);
    t.checkExpect(this.deque4.size(), 0);
  }

  // testing the findData method
  void testFindData(Tester t) {
    this.initializeData();
    t.checkException(new RuntimeException("Can't return data from an empty list"), 
        this.deque1.header, "findData");
    t.checkExpect(this.deque2.header.next.findData(), "abc");
    t.checkExpect(this.deque3.header.prev.findData(), 0);
  }

  // testing the findPrev method
  void testFindNext(Tester t) {
    this.initializeData();
    t.checkExpect(this.deque1.header.prev.findNext(), this.deque1.header);
    t.checkExpect(this.deque2.header.next.findNext(), this.node2);
    t.checkExpect(this.deque3.header.prev.findNext(), this.deque3.header);
  }

  // testing the findPrev method
  void testFindPrev(Tester t) {
    this.initializeData();
    t.checkExpect(this.deque1.header.prev.findPrev(), this.deque1.header);
    t.checkExpect(this.deque2.header.next.findPrev(), this.deque2.header);
    t.checkExpect(this.deque3.header.prev.findNext(), this.deque3.header);
    t.checkExpect(this.deque3.header.next.findNext(), this.node2int);
  }

  // testing the add at head method
  void testAddAtHead(Tester t) {
    this.initializeData();
    this.deque2.addAtHead("ryan");
    this.deque3.addAtHead(56);
    t.checkExpect(this.deque2.header.next.findData(), "ryan");
    t.checkExpect(this.deque3.header.next.findData(), 56);
    t.checkExpect(this.deque2.header.next.findPrev(), this.deque2.header);
    t.checkExpect(this.deque3.header.next.findPrev(), this.deque3.header);
    t.checkExpect(this.deque2.header.next.findNext(), this.node1);
    t.checkExpect(this.deque3.header.next.findNext(), this.node1int);
  }

  // testing the add at tail method
  void testAddAtTail(Tester t) {
    this.initializeData();
    this.deque2.addAtTail("ellie");
    this.deque3.addAtTail(10101010);
    t.checkExpect(this.deque2.header.prev.findData(), "ellie");
    t.checkExpect(this.deque3.header.prev.findData(), 10101010);
    t.checkExpect(this.deque2.header.prev.findPrev(), this.node4);
    t.checkExpect(this.deque3.header.prev.findPrev(), this.node5int);
    t.checkExpect(this.deque2.header.prev.findNext(), this.deque2.header);
    t.checkExpect(this.deque3.header.prev.findNext(), this.deque3.header);
  }

  // testing the remove from head method
  void testRemoveFromHead(Tester t) {
    this.initializeData();
    t.checkException(new RuntimeException("Can't remove a node from an empty list"), 
        this.deque1, "removeFromHead");
    t.checkExpect(this.deque2.removeFromHead(), "abc");
    t.checkExpect(this.deque3.removeFromHead(), 4);
  }

  // testing the remove from tail method
  void testRemoveFromTail(Tester t) {
    this.initializeData();
    t.checkException(new RuntimeException("Can't remove a node from an empty list"), 
        this.deque1, "removeFromTail");
    t.checkExpect(this.deque2.removeFromTail(), "def");
    t.checkExpect(this.deque3.removeFromTail(), 0);
  }

  // testing the find method
  void testFind(Tester t) {
    this.initializeData();
    t.checkExpect(this.deque1.find(containscde), this.deque1.header);
    t.checkExpect(this.deque2.find(containscde), this.node3);
    t.checkExpect(this.deque2.find(containsxyz), this.deque2.header);
    t.checkExpect(this.deque3.find(contains1), this.node4int);
  }

  // testing the remove node method
  void testRemoveNode(Tester t) {
    this.initializeData();

    this.deque1.removeNode(node3);
    t.checkExpect(this.deque1.header, selfreferential);

    this.deque2.removeNode(node3);
    t.checkExpect(this.node2.next, this.node4);

    this.deque3.removeNode(node2int);
    t.checkExpect(this.node1int.next, this.node3int);
  }

  // testing the remove help method
  void testRemoveHelp(Tester t) {
    this.initializeData();

    this.sentstring.removeHelp();
    t.checkExpect(this.deque1.header, selfreferential);

    this.node1.removeHelp();
    t.checkExpect(this.deque2.header.next, this.node2);

    this.node1int.removeHelp();
    t.checkExpect(this.deque3.header.next, this.node2int);
  }

  // testing the apply method 
  void testApply(Tester t) {
    this.initializeData();

    t.checkExpect(containscde.test("cde"), true);
    t.checkExpect(containscde.test("xyz"), false);
    t.checkExpect(contains1.test(1), true);
  }

  //testing the isSentinel method
  void testIsSentinel(Tester t) {
    this.initializeData();
    t.checkExpect(this.deque1.header.isSentinel(), true);
    t.checkExpect(this.deque2.header.next.isSentinel(), false);
  }

  //testing the countNodes method
  void testCountNodes(Tester t) {
    this.initializeData();
    t.checkExpect(this.deque1.header.countNodes(), 0);
    t.checkExpect(this.deque2.header.countNodes(), 4);
    t.checkExpect(this.node4.countNodes(), 1);
  }

  //testing the findHelp method
  void testFindHelp(Tester t) {
    this.initializeData();
    t.checkExpect(this.deque1.header.findHelp(containscde), this.deque1.header);
    t.checkExpect(this.deque2.header.findHelp(containscde), this.deque2.header);
    t.checkExpect(this.node4.findHelp(containscde), this.deque2.header);
    t.checkExpect(this.node2.findHelp(containscde), this.node3);
  }
}

