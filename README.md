# BSTs - Binary Search Tree Implementations & Benchmarking

A comprehensive Java project for implementing, testing, and benchmarking Binary Search Trees (BST) and Red-Black BSTs, along with performance comparisons against QuickSort algorithms.

## Project Overview

This project provides:
- **Tree Implementations**: `AbstractBST` base class with concrete BST and Red-Black BST (RBBST) implementations
- **Interactive TreeShell**: A command-line interface for testing tree operations
- **Comprehensive Benchmarking**: Performance analysis of tree operations vs. quicksort
- **Analysis Notebooks**: Jupyter notebooks for visualizing and analyzing benchmark results

## Features

### 1. Tree Implementations
- **AbstractBST**: Shared generic base class that defines core tree behavior
- **BST (Binary Search Tree)**: Standard (non-self-balancing) binary search tree
- **RBBST (Red-Black BST)**: Self-balancing binary search tree with O(log n) guarantees

### 2. Benchmarking Suite
Measures performance of:
- **Tree Operations**:
  - Insertion
  - Contains (search)
  - Deletion
  - Tree Sort
- **Sorting Algorithms**:
  - QuickSort (unboxed primitive arrays)
  - QuickSort Boxed (Integer objects)

### 3. Array Generation
Test data generation with various distributions:
- `RANDOM`: Random permutations
- `NEARLY_SORTED_1`: 99% pre-sorted
- `NEARLY_SORTED_5`: 95% pre-sorted
- `NEARLY_SORTED_10`: 90% pre-sorted

### 4. TreeShell - Interactive Testing
An interactive command-line interface to manually test tree operations without writing code.

## Project Structure

```
BSTs/
├── Analysis/                        # Notebooks and CSV benchmark outputs
│   ├── SortBench.ipynb
│   ├── TreeBench.ipynb
│   ├── SortBench.csv
│   ├── TreeBench.csv
│   └── res.csv
│
├── src/main/java/io/github/youssefrashidy/
│   ├── Main.java                    # Entry point with menu system
│   ├── ArrayGeneration/             # Test data generators
│   │   ├── ArrayGenerator.java
│   │   └── ArrayType.java
│   ├── Benchmark/                   # Core benchmarking framework
│   │   ├── Benchmarker.java         # Main benchmarking logic
│   │   ├── BenchmarkPrinter.java    # Table/console output formatting
│   │   ├── FeatureExtractor.java    # Statistics calculation
│   │   ├── CSVWriter.java           # CSV export functionality
│   │   ├── Operation.java           # Operation types enum
│   │   ├── Adaptor/                 # Array adaptation layer
│   │   ├── Stat/                    # Execution statistics models
│   │   └── summary/                 # Summary data structures
│   ├── Trees/                       # Tree implementations
│   │   ├── AbstractBST.java         # Abstract BST base class
│   │   ├── BST.java                 # Standard BST
│   │   ├── RBBST.java               # Red-Black BST
│   │   └── Node abstractions
│   ├── quicksort/                   # QuickSort implementations
│   │   ├── QuickSort.java           # Unboxed primitive sort
│   │   └── QuickSortBoxed.java      # Boxed Integer sort
│   └── treeshell/                   # Interactive TreeShell CLI
│       ├── TreeShell.java
│       ├── TreeShellMain.java
│       ├── Tokenizer.java
│       ├── Parser.java
│       ├── nodes/                   # AST node types
│       └── tokens/                  # Token types
│
├── src/test/java/                   # JUnit test suite
│   └── io/github/youssefrashidy/Trees/
│       ├── BSTTest.java
│       └── RBBSTTest.java
│
├── src/main/resources/              # Configuration
│   └── logback.xml                  # Logging configuration
│
├── pom.xml                          # Maven build configuration
└── README.md
```

## Setup & Installation

### Prerequisites
- **Java 24** or higher
- **Maven 3.6+**
- **Python 3.8+** (for Jupyter notebooks)

### Build

```bash
# Clone and navigate to project
cd d:\Study\Intellij\DEMO\BSTs

# Compile with Maven
mvn clean compile

# Build executable JAR
mvn clean package
```

### Virtual Environment (for notebooks)
```bash
# Create virtual environment
python -m venv .venv

# Activate (Windows PowerShell)
.\.venv\Scripts\Activate.ps1

# Activate (Windows CMD)
.venv\Scripts\activate.bat

# Activate (Linux/Mac)
source .venv/bin/activate

# Install dependencies
pip install jupyter pandas matplotlib numpy
```

## Usage

### 1. Interactive Menu (Main Application)

```bash
mvn exec:java -Dexec.mainClass="io.github.youssefrashidy.Main"
```

The menu provides three options:

#### Option 1: TreeShell
Interactive REPL for testing tree operations without code:
```
treeshell> BST t = new BST()
treeshell> t.insert(10, "value10")
treeshell> t.contains(10)
Result: true
treeshell> t.inorder()
Result: [10]
```

Available commands:
- `new BST()` / `new RBBST()` - Create tree
- `tree.insert(key, value)` - Insert element
- `tree.delete(key)` - Remove element
- `tree.contains(key)` - Search element
- `tree.inorder()` - In-order traversal
- `tree.preorder()` - Pre-order traversal
- `tree.postorder()` - Post-order traversal
- `exit` - Exit shell

#### Option 2: Tree Benchmark
Benchmarks tree operations (insert, contains, delete, sort) on 100k-element arrays:
- Compares BST vs. Red-Black BST
- Tests across different input distributions
- Outputs results to CSV (default: `results.csv`)

```
Input file output name/path (Default results.csv) : tree_results.csv
```

#### Option 3: Sort Benchmark
Benchmarks sorting algorithms on 100k-element arrays:
- BST tree sort vs. RB-tree sort vs. QuickSort (unboxed) vs. QuickSort (boxed)
- Tests across different input distributions
- Outputs results to CSV

### 2. Running Tests

```bash
mvn test
```

Tests validate correctness of BST and Red-Black BST implementations:
- Insertion and retrieval
- Deletion and rebalancing
- Tree properties (height, balance)

### 3. Jupyter Notebooks

```bash
# Ensure venv is activated
jupyter notebook

# Or start directly
jupyter notebook Analysis/SortBench.ipynb
```

**Available Notebooks (in `Analysis/`):**
- `Analysis/TreeBench.ipynb`: Interactive analysis of tree operation benchmarks
  - Visualizes insertion/deletion/search performance
  - Compares BST vs. Red-Black BST
  - Shows height distribution across input types

- `Analysis/SortBench.ipynb`: Interactive analysis of sorting benchmarks
  - Compares QuickSort vs. Tree sort
  - Analyzes impact of input distribution
  - Shows speedup ratios

## Key Metrics

Benchmarks measure:
- **Execution Time**: Min, max, mean, median (in milliseconds)
- **Standard Deviation**: Variance in performance
- **Tree Height**: Final tree height after operations
- **Speedup**: Performance ratio (base / current)

## Output Formats

### Console Output
Pretty-printed table with:
- Operation type
- Input distribution
- Data structure used
- Runs count
- Tree height
- Timing statistics
- Speedup factor

### CSV Output
Comma-separated values for further analysis:
- Headers: `operation,distribution,dataStructure,runs,height,min,max,mean,median,std,speedup`
- Times in milliseconds
- Compatible with Excel, pandas, notebooks

## Dependencies

### Runtime
- **SLF4J API** (2.0.13): Logging facade
- **Logback** (1.5.13): Logging implementation
- **Lanterna** (3.1.1): Present as a dependency, but currently not used by the active CLI/benchmark workflow

### Testing
- **JUnit Jupiter** (5.10.0): Unit testing framework

### Analysis
- **Jupyter Notebook**: Interactive analysis
- **pandas**: Data manipulation
- **matplotlib**: Visualization

## Build Configuration

The project is built with Maven using:
- **Java Version**: 24
- **Maven Compiler Plugin**: 3.13.0
- **Maven Shade Plugin**: Creates fat JAR with all dependencies
- **Exec Maven Plugin**: Easy execution of main class

## Performance Insights

The benchmarking suite reveals:
- **Red-Black BST** provides consistent O(log n) performance for all operations
- **Standard BST** performance degrades on sorted/nearly-sorted input
- **QuickSort** offers superior performance on random data
- **Input distribution** significantly impacts tree operation performance

## Logging

Configured via `src/main/resources/logback.xml`:
- Logs to console
- Default level: INFO
- Adjustable per package

## Development Notes

- Uses Java 24 features (records, sealed interfaces)
- Generic tree implementation supporting any comparable types
- Comprehensive statistics collection during benchmarks
- Non-intrusive benchmarking (minimal overhead)

## Testing

Run full test suite:
```bash
mvn test
```

Test coverage includes:
- Tree insertion/retrieval correctness
- Deletion and node rebalancing
- Red-Black tree properties (color, balance)
- Edge cases (empty trees, single elements)

## Future Enhancements

- [ ] AVL tree implementation
- [ ] B-tree benchmarking
- [ ] Multi-threaded benchmark support
- [ ] Real-time visualization during benchmarks
- [ ] Heap and priority queue benchmarks
- [ ] Weighted tree operations

## Author

Youssef Rashidy

## License

This project is provided as-is for educational and research purposes.
