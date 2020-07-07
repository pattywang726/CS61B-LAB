public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> someList = new LinkedListDeque<Character>();

        for (int i = 0; i < word.length(); i += 1){
            char letter = word.charAt(i);
            someList.addLast(letter);
        }
        return someList;
    }

    /* return true if the given word is a palindrome */
    /* helper method */
    private boolean isPalindromeHelper(Deque dl){
        if (dl.size() == 0 || dl.size() == 1){
            return true;
        } else if (dl.removeFirst() == dl.removeLast()){
            return isPalindromeHelper(dl);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word){
        Deque dl = wordToDeque(word);
        return isPalindromeHelper(dl);
    }

    /* overload the isPalindrome Method */
    private boolean isPalindromeHelper(Deque dl, CharacterComparator cc){
        if (dl.size() == 0 || dl.size() == 1){
            return true;
        } else if (cc.equalChars((char)dl.removeFirst(), (char)dl.removeLast())){
            return isPalindromeHelper(dl, cc);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque dl = wordToDeque(word);
        return isPalindromeHelper(dl, cc);
    }
}
