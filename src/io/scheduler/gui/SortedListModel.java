package io.scheduler.gui;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

public class SortedListModel<E> extends AbstractListModel<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6150903636354738067L;
	SortedSet<E> model;

	public SortedListModel() {
		model = new TreeSet<E>();
	}

	@Override
	public int getSize() {
		return model.size();
	}

	@Override
	@SuppressWarnings("unchecked")
	public E getElementAt(int index) {
		return (E) model.toArray()[index];
	}

	public void add(E element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(E elements[]) {
		Collection<E> c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(E element) {
		return model.contains(element);
	}

	public Object firstElement() {
		return model.first();
	}

	public Iterator<E> iterator() {
		return model.iterator();
	}

	public E lastElement() {
		return model.last();
	}

	public boolean removeElement(E element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}

	public boolean isEmpty() {
		return model.isEmpty();
	}
}
