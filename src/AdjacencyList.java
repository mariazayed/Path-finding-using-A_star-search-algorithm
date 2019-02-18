import java.util.*;

public class AdjacencyList<Item> implements Iterable<Item> {
	private Node<Item> header; // beginning of the AdjacencyList
	private int numberOfAdjacentCities;

	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}

	// Default Constructor with initial values
	public AdjacencyList() {
		header = null;
		numberOfAdjacentCities = 0;
	}

	// check if this AdjacencyList is empty
	public boolean isEmpty() {
		return header == null;
	}

	// getter
	public int numberOfAdjacentCities() {
		return numberOfAdjacentCities;
	}

	// adds the item to this AdjacencyList
	public void add(Item item) {
		Node<Item> first = header;
		header = new Node<Item>();
		header.item = item;
		header.next = first;
		numberOfAdjacentCities++;
	}

	// returns an iterator that iterates over the items in this AdjacencyList
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(header);
	}

	@SuppressWarnings("hiding")
	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}
