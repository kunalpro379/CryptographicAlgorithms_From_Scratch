def get_order(key):
    return [i for _, i in sorted((char, i) for i, char in enumerate(key))]

def encrypt(text, row_key, col_key):
    text = text.replace(" ", "").upper()
    num_rows = len(row_key)
    num_cols = len(col_key)

    total_len = num_rows * num_cols
    text += 'X' * (total_len - len(text))

    # Fill matrix row-wise
    matrix = [list(text[i*num_cols:(i+1)*num_cols]) for i in range(num_rows)]

    # Row transposition
    row_order = get_order(row_key)
    row_transposed = [matrix[i] for i in row_order]

    # Column transposition
    col_order = get_order(col_key)
    final_matrix = [[row[i] for i in col_order] for row in row_transposed]

    # Read encrypted text
    cipher = ''.join(''.join(row) for row in final_matrix)
    return cipher

# Example usage
text = "WE ARE DISCOVERED"
row_key = "ZEBRAS"
col_key = "KEY"

encrypted_text = encrypt(text, row_key, col_key)
print("Encrypted Text:", encrypted_text)
