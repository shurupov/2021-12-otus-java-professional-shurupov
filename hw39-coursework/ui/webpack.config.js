const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    entry: "./src/index.tsx",
    output: { path: path.join(__dirname, "build"), filename: "index.bundle.js" },
    mode: process.env.NODE_ENV || "development",
    resolve: {
        extensions: [".tsx", ".ts", ".js"],
        alias: {
            components: path.resolve(__dirname, "src/components"),
            store: path.resolve(__dirname, "src/store"),
            api: path.resolve(__dirname, "src/api"),
        },
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: ["babel-loader"],
            },
            {
                test: /\.(ts|tsx)$/,
                exclude: /node_modules/,
                use: ["ts-loader"],
            },
            {
                test: /\.(css|scss)$/,
                use: ["style-loader", "css-loader"],
            },
            {
                test: /\.(jpg|jpeg|png|gif|mp3|svg)$/,
                use: ["file-loader"],
            },
        ],
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.join(__dirname, "src", "index.html"),
        }),
    ],
    // devtool: "inline-source-map",

    devServer: {
        contentBase: path.join(__dirname, "build"),
        historyApiFallback: true,
        port: 4000,
        open: true,
        hot: true,
        before: (app) => {
            app.get('/core/api/apartments', (req, res) => res.send([
                {
                    id: 1,
                    apartmentType: { id: 1, name: "Квартира"},
                    number: 1,
                    floor: 1,
                    square: 50.5
                },
                {
                    id: 2,
                    apartmentType: { id: 1, name: "Квартира"},
                    number: 2,
                    floor: 1,
                    square: 70
                },
                {
                    id: 3,
                    apartmentType: { id: 1, name: "Квартира"},
                    number: 3,
                    floor: 2,
                    square: 50.5
                },
                {
                    id: 4,
                    apartmentType: { id: 1, name: "Квартира"},
                    number: 4,
                    floor: 2,
                    square: 70
                },
            ]));
        }
    },
};