package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

	public <X, Y> SubsequenceCombiner<X, Y> generic(Predicate<List<X>> condition, Function<List<X>, Y> action)
	{
		return new SubsequenceCombiner<X,Y>() {

			@Override
			public List<Y> combine(List<X> list) {
				List<X> tempList = new ArrayList<>();
				List<Y> outList = new ArrayList<>();
				
				for (X element : list) {
					tempList.add(element);
					if(condition.test(tempList)) {
						outList.add(action.apply(tempList));
						tempList.clear();
					}
				}
				
				if(!tempList.isEmpty()) {
					outList.add(action.apply(tempList));
				}

				return outList;
			}
			
		};
	}

	@Override
	public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
		return generic(list -> list.size() == 3, list -> list.stream().mapToInt(elem -> elem).sum());
	}

	@Override
	public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
		//return generic(list -> list.size() == 3, ArrayList::new);
		return generic(list -> list.size() == 3, list -> new ArrayList<>(list));
	}

	@Override
	public SubsequenceCombiner<Integer, Integer> countUntilZero() {
		return generic(list -> list.get(list.size() - 1) == 0, list -> list.get(list.size() - 1) == 0 ? list.size() - 1 : list.size());
	}

	@Override
	public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
		return generic(list -> list.size() == 1, list -> function.apply(list.get(0)));
	}

	@Override
	public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
		return generic(list -> list.stream().mapToInt(elem -> elem).sum() >= 100, list -> new ArrayList<>(list));
	}



}
