public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> L = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            L.addLast(word.charAt(i));
        }
        return L;
    }

    public boolean isPalindrome(String word) {
        // A common approach to implement the method.
        /*
        int len = word.length();
        for (int i = 0; i < len / 2; i++) {
            if (word.charAt(i) != word.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
        */
        // An approach using the written data structure.
        Deque<Character> L = wordToDeque(word);
        return isPalindromeHelper(L);
    }

    public boolean isPalindromeHelper(Deque L) {
        if (L.size() == 0 || L.size() == 1) {
            return true;
        }
        if (L.removeFirst() != L.removeLast()) {
            return false;
        } else {
            return isPalindromeHelper(L);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> L = wordToDeque(word);
        return isPalindromeHelper(L, cc);
    }

    public boolean isPalindromeHelper(Deque<Character> L, CharacterComparator cc) {
        if (L.size() == 0 || L.size() == 1) {
            return true;
        }
        if (cc.equalChars(L.removeFirst(), L.removeLast())) {
            return isPalindromeHelper(L, cc);
        } else {
            return false;
        }
    }
}
