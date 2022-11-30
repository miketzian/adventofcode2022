package day1

import (
	_ "embed"
	"fmt"
	"testing"
)

var (
	//go:embed test.txt
	testData string

	//go:embed input.txt
	inputData string
)

func TestDayOne(t *testing.T) {

	// parse input data

	t.Run("Parse Test", func(t *testing.T) {

	})

	t.Run("Part One", func(t *testing.T) {

		cases := []struct {
			input    []int
			expected int64
		}{
			{
				[]int{1721, 979, 366, 299, 675},
				int64(0),
			},
		}
		for ix, c := range cases {
			t.Run(fmt.Sprintf("case %d", ix), func(t *testing.T) {
				result, err := partOne(c.input)
				if err != nil {
					t.Fatalf("Error: %s\n", err)
				}
				if c.expected == -1 {
					// we don't know the answer, we're using
					// the computation
					t.Logf("Result: %d\n", result)
					return
				}
				if result != c.expected {
					t.Fatalf("Expected Total %d, Actual %d\n",
						c.expected, result)
				}
			})
		}
	})

}
