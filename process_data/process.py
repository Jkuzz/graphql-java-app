import pandas as pd


def count_script_lines(script_df):
    script_df['char'] = script_df['char'].apply(str.strip)
    script_df['char'] = script_df['char'].replace({'ARGORN': 'ARAGORN', '(GOLLUM': 'GOLLUM'})
    return script_df.groupby(['char', 'movie']).agg(func='count')['dialog']


def main():
    chars_df = pd.read_csv('data/year.csv')
    script_df = pd.read_csv('data/lotr_scripts.csv')

    total_lines = count_script_lines(script_df)
    df_lines = chars_df['name'].apply(
            get_movie_lines,
            args=('The Fellowship of the Ring', total_lines)
        )
    print(df_lines[df_lines > 0])
    chars_df.assign(movie1_lines=df_lines)
    # chars_df.assign(movie2_lines=get_movie_lines, args=('The Two Towers', total_lines))
    # chars_df.assign(movie3_lines=get_movie_lines, args=('The Return of the King', total_lines))


def get_movie_lines(character, movie, total_lines):
    char_name = character.strip().upper()
    if char_name not in total_lines:  # Character had no lines in any movie
        return 0
    lines = total_lines[char_name]
    movie_lines = lines[lines.index.str.startswith(movie)]
    if movie_lines.empty:  # Character did not have line in the movie
        return 0
    return movie_lines[0]


if __name__ == '__main__':
    main()
