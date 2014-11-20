package com.verba.language.parsing.info;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 14-2-20.
 */
public class LexList implements Iterable<LexInfo> {
  List<LexInfo> list = new ArrayList<>();

  public LexInfo get(int index) {
    return this.list.get(index);
  }

  public int length() {
    return this.list.size();
  }

  public <T> boolean contains(Class<T> type, String representation) {
    for (LexInfo token : list) if (token.is(type, representation)) return true;
    return false;
  }

  public boolean contains(String representation) {
    for (LexInfo token : list) if (token.is(representation)) return true;
    return false;
  }

  public <T> boolean contains(Class<T> type) {
    for (LexInfo token : list) if (token.is(type)) return true;
    return false;
  }

  public <T> boolean startsWith(Class<T> type, String representation) {
    if (list.size() == 0) return false;
    else return list.get(0).is(type, representation);
  }

  public <T> boolean startsWith(Class<T> type) {
    if (list.size() == 0) return false;
    else return list.get(0).is(type);
  }

  public boolean startsWith(String representation) {
    if (list.size() == 0) return false;
    else return list.get(0).is(representation);
  }

  public <T> boolean endsWith(Class<T> type, String representation) {
    int lastIndex = list.size() - 1;

    if (lastIndex < 0) return false;
    else return list.get(lastIndex).is(type, representation);
  }

  public <T> boolean endsWith(Class<T> type) {
    int lastIndex = list.size() - 1;

    if (lastIndex < 0) return false;
    else return list.get(lastIndex).is(type);
  }

  public boolean startsWithSequence(TokenSignature... tokenSignatures) {
    if (tokenSignatures.length == 0) return false;
    if (tokenSignatures.length > this.list.size()) return false;

    for (int index = 0; index < tokenSignatures.length; index++) {
      LexInfo tokenAtIndex = this.list.get(index);
      if (tokenSignatures[index].tokenMatchesThisSignature(tokenAtIndex) == false) {
        return false;
      }
    }

    return true;
  }

  public <T> int count(Class<T> type, String representation) {
    int total = 0;

    for (LexInfo info : this.list) {
      if (info.is(type, representation)) total += 1;
    }

    return total;
  }

  public <T> int count(Class<T> type) {
    int total = 0;

    for (LexInfo info : this.list) {
      if (info.is(type)) total += 1;
    }

    return total;
  }

  public int count(String representation) {
    int total = 0;

    for (LexInfo info : this.list) {
      if (info.is(representation)) total += 1;
    }

    return total;
  }

  public <T> int count(Predicate<LexInfo> test) {
    int total = 0;
    for (LexInfo info : this.list) {
      if (test.test(info)) total += 1;
    }
    return total;
  }

  public <T> List<Integer> getOffsetsForTokensMatching(Class<T> type, String representation) {
    List<Integer> offsets = new ArrayList<Integer>();
    for (int index = 0; index < this.list.size(); index++) {
      if (this.list.get(index).is(type, representation)) offsets.add(index);
    }

    return offsets;
  }

  public <T> List<Integer> getOffsetsForTokensMatching(String representation) {
    List<Integer> offsets = new ArrayList<Integer>();
    for (int index = 0; index < this.list.size(); index++) {
      if (this.list.get(index).is(representation)) offsets.add(index);
    }

    return offsets;
  }

  public <T> List<Integer> getOffsetsForTokensMatching(Class<T> type) {
    List<Integer> offsets = new ArrayList<Integer>();
    for (int index = 0; index < this.list.size(); index++) {
      if (this.list.get(index).is(type)) offsets.add(index);
    }

    return offsets;
  }

  public <T> List<Integer> getOffsetsForTokensMatching(Predicate<LexInfo> test) {
    List<Integer> offsets = new ArrayList<Integer>();
    for (int index = 0; index < this.list.size(); index++) {
      if (test.test(this.list.get(index))) offsets.add(index);
    }

    return offsets;
  }

  public <T> int getIndexOfFirst(Class<T> type, String representation) {
    for (int index = 0; index < this.list.size(); index++) {
      if (this.list.get(index).is(type, representation)) return index;
    }

    return -1;
  }

  public int getIndexOfFirst(String representation) {
    for (int index = 0; index < this.list.size(); index++) {
      if (this.list.get(index).is(representation)) return index;
    }

    return -1;
  }

  public <T> int getIndexOfFirst(Class<T> type) {
    for (int index = 0; index < this.list.size(); index++) {
      if (this.list.get(index).is(type)) return index;
    }

    return -1;
  }

  public <T> int getIndexOfFirst(TokenSignature signature) {
    for (int index = 0; index < this.list.size(); index++) {
      if (signature.tokenMatchesThisSignature(this.get(index))) return index;
    }

    return -1;
  }

  public <T> int getIndexOfLast(TokenSignature signature) {
    for (int index = this.list.size(); index > 0; index--) {
      if (signature.tokenMatchesThisSignature(this.get(index - 1))) return index;
    }

    return -1;
  }

  public <T> int getIndexOfLast(Class<T> type, String representation) {
    for (int index = this.list.size(); index > 0; index--) {
      if (this.list.get(index - 1).is(type, representation)) return index;
    }

    return -1;
  }

  public int getIndexOfLast(String representation) {
    for (int index = this.list.size(); index > 0; index--) {
      if (this.list.get(index - 1).is(representation)) return index;
    }

    return -1;
  }

  public <T> int getIndexOfLast(Class<T> type) {
    for (int index = this.list.size(); index > 0; index--) {
      if (this.list.get(index - 1).is(type)) return index;
    }

    return -1;
  }

  public LexList getSubset(int start, int end) {
    LexList subset = new LexList();
    for (int index = start; index < end; index++) {
      subset.add(this.list.get(index));
    }

    return subset;
  }

  public LexList getSubset(TokenSignature start, TokenSignature end) {
    int startIndex = this.getIndexOfFirst(start);
    int endIndex = this.getIndexOfLast(end);

    if ((startIndex != -1) && (endIndex != -1)) {
      return this.getSubset(startIndex, endIndex);
    }

    return null;
  }

  public int getStartOfSequence(TokenSignature... tokenSignatures) {
    if (tokenSignatures.length == 0) return -1;
    if (tokenSignatures.length > this.list.size()) return -1;

    TokenSignature firstTokenSignature = tokenSignatures[0];
    for (int index = 0; index < this.list.size(); index++) {
      LexInfo currentTestToken = this.list.get(index);

      // If the current outside item matches the first item in the sequence,
      // then start testing each item.
      if (firstTokenSignature.tokenMatchesThisSignature(currentTestToken)) {
        int startOfSequence = index;
        for (int testIndex = 0; testIndex < tokenSignatures.length; testIndex++) {
          currentTestToken = this.list.get(index);
          index += 1; // Increment the outside loop

          if (!tokenSignatures[testIndex].tokenMatchesThisSignature(currentTestToken)) break;
          else if (testIndex == tokenSignatures.length - 1) return startOfSequence;
        }
      }
    }

    return -1;
  }

  public boolean containsSequence(TokenSignature... tokenSignatures) {
    return this.getStartOfSequence(tokenSignatures) != -1;
  }

  public boolean containsInThisOrder(TokenSignature... tokenSignatures) {
    int signatureIndex = 0;
    int totalSignatures = tokenSignatures.length;

    if (totalSignatures == 0) return false;
    for (LexInfo token : this.list) {
      if (tokenSignatures[signatureIndex].tokenMatchesThisSignature(token)) signatureIndex++;
      if (signatureIndex == totalSignatures) return true;
    }

    return false;
  }

  public boolean endsWithSequence(TokenSignature... tokenSignatures) {
    if (tokenSignatures.length == 0) return false;
    if (tokenSignatures.length > this.list.size()) return false;

    int offset = this.list.size() - tokenSignatures.length;
    for (int index = 0; index < tokenSignatures.length; index++) {
      LexInfo tokenAtOffsetIndex = this.list.get(offset + index);

      if (tokenSignatures[index].tokenMatchesThisSignature(tokenAtOffsetIndex) == false) {
        return false;
      }
    }

    return true;
  }

  public boolean endsWith(String representation) {
    int lastIndex = list.size() - 1;

    if (lastIndex < 0) return false;
    else return list.get(lastIndex).is(representation);
  }

  public void add(LexInfo lexInfo) {
    this.list.add(lexInfo);
  }

  @Override
  public Iterator<LexInfo> iterator() {
    return this.list.iterator();
  }

  public LexInfo first() {
    return this.get(0);
  }

  public LexInfo last() {
    return this.get(this.list.size() - 1);
  }
}
