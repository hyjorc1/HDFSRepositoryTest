package Serialization;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyFile extends java.io.File implements Serializable {

	private static final long serialVersionUID = 1L;

	private FileNode node;

	public MyFile(FileNode node) {
		super(node.getName());
		this.node = node;
	}

	/* -- Path-component accessors -- */

	@Override
	public String getName() {
		String name = node.getName();
		int index = name.lastIndexOf(separatorChar);
		return name.substring(index + 1);
	}

	@Override
	public String getParent() {
		String name = node.getName();
		int index = name.lastIndexOf(separatorChar);
		return name.substring(0, index);
	}

	@Override
	public MyFile getParentFile() {
		return new MyFile(this.node.getParent());
	}

	@Override
	public String getPath() {
		return this.node.getName();
	}

	@Override
	public boolean isAbsolute() {
		return true;
	}

	@Override
	public String getAbsolutePath() {
		return getPath();
	}

	@Override
	public MyFile getAbsoluteFile() {
		return this;
	}

	@Override
	public String getCanonicalPath() {
		return getPath();
	}

	@Override
	public MyFile getCanonicalFile() {
		return this;
	}

	/* -- Attribute accessors -- */

	@Override
	public boolean canRead() {
		return true;
	}

	@Override
	public boolean canWrite() {
		return false;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public boolean isDirectory() {
		return this.node.isDirectory();
	}

	@Override
	public boolean isFile() {
		return !this.node.isDirectory();
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public long lastModified() {
		return 0L;
	}

	@Override
	public long length() {
		return 0L;
	}

	/* -- File operations -- */

	@Override
	public boolean createNewFile() throws IOException {
		return false;
	}

	@Override
	public boolean delete() {
		if (node.getParent() != null && node.getIdx() != -1) {
			node.getParent().getList().remove(node.getIdx());
			return true;
		}
		return false;
	}

	@Override
	public void deleteOnExit() {

	}

	@Override
	public String[] list() {
		List<FileNode> list = node.getList();
		if (node.getList() != null) {
			String[] arr = new String[list.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = list.get(i).getName();
		}
		return null;
	}

	@Override
	public String[] list(FilenameFilter filter) {
		String names[] = list();
		if ((names == null) || (filter == null)) {
			return names;
		}
		List<String> v = new ArrayList<>();
		for (int i = 0; i < names.length; i++) {
			if (filter.accept(this, names[i])) {
				v.add(names[i]);
			}
		}
		return v.toArray(new String[v.size()]);
	}

	@Override
	public MyFile[] listFiles() {
		List<FileNode> list = node.getList();
		if (list == null)
			return null;
		int n = list.size();
		MyFile[] fs = new MyFile[n];
		for (int i = 0; i < n; i++) {
			fs[i] = new MyFile(list.get(i));
		}
		return fs;
	}

	@Override
	public MyFile[] listFiles(FilenameFilter filter) {
		List<FileNode> list = node.getList();
		if (list == null)
			return null;
		ArrayList<File> files = new ArrayList<>();
		for (FileNode n : list)
			if ((filter == null) || filter.accept(this, n.getName()))
				files.add(new MyFile(n));
		return files.toArray(new MyFile[files.size()]);
	}

	@Override
	public MyFile[] listFiles(FileFilter filter) {
		List<FileNode> list = node.getList();
		if (list == null)
			return null;
		ArrayList<MyFile> files = new ArrayList<>();
		for (FileNode n : list) {
			MyFile f = new MyFile(n);
			if ((filter == null) || filter.accept(f))
				files.add(f);
		}
		return files.toArray(new MyFile[files.size()]);
	}

	@Override
	public boolean mkdir() {
		return true;
	}

	@Override
	public boolean mkdirs() {
		return true;
	}

	@Override
	public boolean renameTo(File dest) {
		return true;
	}

	@Override
	public boolean setLastModified(long time) {
		return true;
	}

	@Override
	public boolean setReadOnly() {
		return true;
	}

	@Override
	public boolean setWritable(boolean writable, boolean ownerOnly) {
		return true;
	}

	@Override
	public boolean setWritable(boolean writable) {
		return true;
	}

	@Override
	public boolean setReadable(boolean readable, boolean ownerOnly) {
		return true;
	}

	@Override
	public boolean setReadable(boolean readable) {
		return true;
	}

	@Override
	public boolean setExecutable(boolean executable, boolean ownerOnly) {
		return true;
	}

	@Override
	public boolean setExecutable(boolean executable) {
		return true;
	}

	@Override
	public boolean canExecute() {
		return false;
	}

	/* -- Filesystem interface -- */
	/* -- Disk usage -- */
	/* -- Temporary files -- */
	/* -- Basic infrastructure -- */
	public int compareTo(MyFile file) {
		return node.getName().compareTo(file.node.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj != null) && (obj instanceof MyFile))
			return compareTo((MyFile) obj) == 0;
		return false;
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	@Override
	public String toString() {
		return getPath();
	}

//	@Override
//	private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
//		s.defaultWriteObject();
//		s.writeChar(separatorChar); // Add the separator character
//	}

}
