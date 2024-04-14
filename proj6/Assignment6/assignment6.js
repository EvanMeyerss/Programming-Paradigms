////////////////////////////////////////////////////////////
//  CSCE 3193
//  Spring 2024
//  Assignment 6
//  Name: Evan Meyers
////////////////////////////////////////////////////////////


//  You must complete the implementation of the six functions below.
//  In all cases where it is given that the arguments to a function are
//  numbers (or an array of numbers), you can assume that the correct types
//  of values are passed as arguments and don't have to check for the wrong
//  types or invalid input in the script used for testing.


////////////////////////////////////////////////////////////
//  Recursion


function reverseArray(arr) {
	var arrCopy = []
	if(arr.length === 0) {
		return arrCopy
	} else {
		// .slice(1) takes all elements in the array from index 1 to the end, discarding index 0.
		// index 0 is used as the second parameter for the concat field. Doing this puts the 0 index at the end of the array
		// ex:: 
        //      input: [a,b,c,d]
		// 		after: 
        //          1st recursive call:: output: [b,c,d]
        //          2nd recursive call:: output: [c,d]
        //          3rd recursive call:: output: [d]
        //      working backwords, we place add the output with the first element of the parent call
        //          1st: [d] plus element 0 from [c,d], 'c'. ---------- output:: [d,c]
        //          2nd: [d,c] plus element 0 from [b,c,d], 'b'. ------ output:: [d,c,b]
        //          3rd: [d,c,b] plus element 0 from [a,b,c,d] 'a'. --- output:: [d,c,b,a] <==== RESULT
        return arrCopy.concat(reverseArray(arr.slice(1)), arr[0])
	}
}

function findMin(a) {
    // sets variables equal to first two elements of array
    var checkFirst = a[0]
    var checkSecond = a[1]
	
    // if there is only one element in array, it is the minimum value
    if(a.length === 1) {
		return a[0]
	} 
    else {
        // if 1st element >= 2nd element, remove the 1st element from array and rerun function
        if (checkFirst >= checkSecond) {
            return findMin(a.slice(1))
        }
        else {
            // 2nd element > 1st element, remove 2nd element from array and rerun function 
            // splice used to directly remove from original array
            a.splice(1,1)
            return findMin(a)
        }
	}
}

function stringFromArrays(arr1, arr2) {
	
    // if there is only one element in either array, return concatination of '' + ''
    if(arr1.length === 0 || arr2.length === 0) {
		return arr1.concat(arr2)
	} 
    else {
        // concats first element from each array and then passes slices of the 
        //      following elements of the array into the function again until array lengths === 0
        // 'String(...)' is used to cast the array elements to a String so ints concatinate instead of adding
        return String(arr1[0]) + String(arr2[0]) + stringFromArrays(arr1.slice(1), arr2.slice(1))
	}
}


////////////////////////////////////////////////////////////
//  Higher order functions

function applyToArray(a) {
    
    return function(f) {
        // as covered in class, map applies a change to each element of an array
        // in this case, the function passed in as a parameter is applied to each element
        // ex:: 
        //      const fun = applyToArray([1,2,3,4])
        //      console.log(fun(add2)) ------ output:: [3,4,5,6]
        return a.map(f)
    }
}

function computeMaxArr(f1, f2) {
    
    return function(a) {
        // if there is only one element in the array, it checks within this if statement 
        //      instead of doing another recursive call in the block below
        if(a.length === 1) {
            // returns the max int between the two function operations
            return Math.max(f1(a[0]), f2(a[0]))
        }
        else {
            // first finds which of two function results is greatest for first element in array
            const firstElementResult = Math.max(f1(a[0]), f2(a[0]));
            // now, concatinate to the 'firstElementResult' the max of each subsequent element-function operations
            // to do this, recursively call computeMaxArr(...) function
            // each iteration processes one more element from the front of the list
            // continues until the length of the array equals 1, where the upper if statement is run
            // collapses the 'recursive tree' and concatinates the max values from the function operations into the array in order
            return [firstElementResult].concat(computeMaxArr(f1,f2)(a.slice(1)))
        }
    }
}


////////////////////////////////////////////////////////////
//  Common closure

function makeClosure() {
    
    // iterating counter
    let count = 0
    // empty arary
    let a = []
    
    // first function that takes in the two int parameters
    // adds ints together and stores into top of array IF count < 3
    function addToArr(num1, num2) {
        if (count < 3) {
            a.push(num1 + num2)
        }
        // returns the array
        return a
    }
    
    // second function that simply increments the count
    function incrementCount() {
        count++
    }
    
    // returns both functions
    return [addToArr, incrementCount]
}

////////////////////////////////////////////////////////////
//  Auxiliary functions (used for testing)

function mult4(i) {
	return i*4;
}

function add2(j) {
	return j+2;
}

function square(k) {
	return k*k;
}
