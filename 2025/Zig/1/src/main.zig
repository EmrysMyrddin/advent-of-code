const std = @import("std");

const Part = enum(u8) { one = '1', two = '2' };

pub fn main() !void {
    const part: Part = if (std.os.argv[1][0] == '1') .one else .two;
    var numberOf0: u32 = 0;
    var currentDialPosition: i32 = 50;

    var inputFile = std.fs.cwd().openFile("input", .{ .mode = .read_only }) catch |err| {
        if (err == error.FileNotFound) {
            std.debug.print("Missing input file: ./input\n", .{});
            return;
        }
        return;
    };
    defer inputFile.close();

    var buff: [512]u8 = undefined;
    var inputFileReader = inputFile.reader(&buff);
    var inputReader = &inputFileReader.interface;

    while (true) {
        const directionString = inputReader.take(1) catch |err| {
            if (err == error.EndOfStream) break else return err;
        };
        const direction: i32 = if (directionString[0] == 'L') -1 else 1;

        const n = try std.fmt.parseInt(i32, try inputReader.takeDelimiter('\n') orelse unreachable, 10);
        std.debug.print("Parsed: {d} {d} | {d} | {d}.\n", .{ direction, n, currentDialPosition, numberOf0 });

        const rotation = n * direction;
        const previousPositon = currentDialPosition;
        currentDialPosition += rotation;

        if (part == .two) {
            if (currentDialPosition == 0 or
                (currentDialPosition > 0 and previousPositon < 0) or
                (currentDialPosition < 0 and previousPositon > 0))
            {
                numberOf0 += 1;
            }
            numberOf0 += @abs(@divTrunc(currentDialPosition, 100));
        }

        currentDialPosition = @rem(currentDialPosition, 100);

        if (part == .one) {
            if (currentDialPosition == 0) {
                numberOf0 += 1;
            }
        }
    }

    std.debug.print("Answer: {d}.\n", .{numberOf0});
}
